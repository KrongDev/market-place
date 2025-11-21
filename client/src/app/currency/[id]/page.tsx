"use client";

import { useEffect, useState } from 'react';
import { useParams, useRouter } from 'next/navigation';
import { currencyService } from '@/services/currencyService';
import { chatService } from '@/services/chatService';
import { authService } from '@/services/authService';
import styles from '@/app/exchange/[id]/detail.module.css'; // Reuse detail styles

interface CurrencyListing {
    id: number;
    amount: number;
    price: number;
    sellerId: number;
    buyerId?: number;
    status: string;
}

interface User {
    id: number;
    userId: number;
}

export default function CurrencyDetailPage() {
    const { id } = useParams();
    const router = useRouter();
    const [listing, setListing] = useState<CurrencyListing | null>(null);
    const [currentUser, setCurrentUser] = useState<User | null>(null);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            const data = await currencyService.getListingDetail(Number(id));
            setListing(data);
            setCurrentUser(authService.getCurrentUser());
        };
        fetchData();
    }, [id]);

    if (!listing) return <div className="container">Loading...</div>;

    const isSeller = currentUser && String(currentUser.userId) === String(listing.sellerId);
    const isBuyer = currentUser && String(currentUser.userId) === String(listing.buyerId);

    const handleRequestTrade = async () => {
        if (!currentUser) return router.push('/login');
        setLoading(true);
        try {
            await currencyService.requestListing(Number(id), currentUser.userId);
            alert("Trade Requested!");
            window.location.reload();
        } catch (e) {
            alert("Failed to request trade");
        } finally {
            setLoading(false);
        }
    };

    const handleAcceptTrade = async () => {
        setLoading(true);
        try {
            await currencyService.acceptListing(Number(id), currentUser!.userId);
            const roomId = await chatService.createRoom(Number(id), listing.sellerId, listing.buyerId!);
            router.push(`/chat/${roomId}`);
        } catch (e) {
            alert("Failed to accept trade");
        } finally {
            setLoading(false);
        }
    };

    const handleGoToChat = async () => {
        const roomId = await chatService.createRoom(Number(id), listing.sellerId, listing.buyerId || currentUser!.userId);
        router.push(`/chat/${roomId}`);
    };

    return (
        <div className="container" style={{ padding: '4rem 1rem' }}>
            <div className={styles.layout}>
                <div className={styles.imageSection}>
                    <div className={styles.imagePlaceholder} style={{ color: 'gold' }}>💰</div>
                </div>

                <div className={styles.infoSection}>
                    <h1 className={styles.title}>{listing.amount} Gold</h1>
                    <div className={styles.price}>${listing.price.toFixed(2)}</div>

                    <div className={styles.meta}>
                        <p>Seller: User #{listing.sellerId}</p>
                        <p>Status: <span className={styles.status}>{listing.status}</span></p>
                    </div>

                    <div className={styles.actions}>
                        {listing.status === 'ON_SALE' && !isSeller && (
                            <button className="btn btn-primary" onClick={handleRequestTrade} disabled={loading}>
                                Request Trade
                            </button>
                        )}

                        {listing.status === 'REQUESTED' && isSeller && (
                            <button className="btn btn-primary" onClick={handleAcceptTrade} disabled={loading}>
                                Accept Trade request from User #{listing.buyerId}
                            </button>
                        )}

                        {listing.status === 'REQUESTED' && isBuyer && (
                            <button className="btn btn-outline" disabled>
                                Waiting for Seller Acceptance...
                            </button>
                        )}

                        {listing.status === 'ACCEPTED' && (isSeller || isBuyer) && (
                            <button className="btn btn-primary" onClick={handleGoToChat}>
                                Go to Chat Room
                            </button>
                        )}

                        {listing.status === 'COMPLETED' && (
                            <div className="card">
                                <h3>Trade Completed</h3>
                                <button className="btn btn-outline" style={{ marginTop: '1rem' }}>View Receipt</button>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}
