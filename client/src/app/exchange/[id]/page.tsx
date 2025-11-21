"use client";

import { useEffect, useState } from 'react';
import { useParams, useRouter } from 'next/navigation';
import { tradeService } from '@/services/tradeService';
import { chatService } from '@/services/chatService';
import { authService } from '@/services/authService';
import styles from './detail.module.css';

interface TradeItem {
    id: number;
    itemName: string;
    price: number;
    sellerId: number;
    buyerId?: number;
    status: string;
    options?: Record<string, any>;
}

interface User {
    id: number;
    userId: number;
}

export default function ItemDetailPage() {
    const { id } = useParams();
    const router = useRouter();
    const [item, setItem] = useState<TradeItem | null>(null);
    const [currentUser, setCurrentUser] = useState<User | null>(null);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            const data = await tradeService.getTradeDetail(Number(id));
            setItem(data);
            setCurrentUser(authService.getCurrentUser());
        };
        fetchData();
    }, [id]);

    if (!item) return <div className="container">Loading...</div>;

    const isSeller = currentUser && String(currentUser.userId) === String(item.sellerId);
    const isBuyer = currentUser && String(currentUser.userId) === String(item.buyerId);

    const handleRequestTrade = async () => {
        if (!currentUser) return router.push('/login');
        setLoading(true);
        try {
            await tradeService.requestTrade(Number(id), currentUser.userId);
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
            await tradeService.acceptTrade(Number(id), currentUser!.userId);
            // Create Chat Room
            const roomId = await chatService.createRoom(Number(id), item.sellerId, item.buyerId!);
            router.push(`/chat/${roomId}`);
        } catch (e) {
            alert("Failed to accept trade");
        } finally {
            setLoading(false);
        }
    };

    const handleGoToChat = async () => {
        // Find existing room (simplified: createRoom returns existing if present)
        const roomId = await chatService.createRoom(Number(id), item.sellerId, item.buyerId || currentUser!.userId);
        router.push(`/chat/${roomId}`);
    };

    return (
        <div className="container" style={{ padding: '4rem 1rem' }}>
            <div className={styles.layout}>
                <div className={styles.imageSection}>
                    <div className={styles.imagePlaceholder}>⚔️</div>
                </div>

                <div className={styles.infoSection}>
                    <h1 className={styles.title}>{item.itemName}</h1>
                    <div className={styles.price}>{item.price.toLocaleString()} Gold</div>

                    <div className={styles.meta}>
                        <p>Seller: User #{item.sellerId}</p>
                        <p>Status: <span className={styles.status}>{item.status}</span></p>
                    </div>

                    <div className={styles.actions}>
                        {/* ON_SALE: Buyer can Request */}
                        {item.status === 'ON_SALE' && !isSeller && (
                            <button className="btn btn-primary" onClick={handleRequestTrade} disabled={loading}>
                                Request Trade
                            </button>
                        )}

                        {/* REQUESTED: Seller can Accept */}
                        {item.status === 'REQUESTED' && isSeller && (
                            <button className="btn btn-primary" onClick={handleAcceptTrade} disabled={loading}>
                                Accept Trade request from User #{item.buyerId}
                            </button>
                        )}

                        {/* REQUESTED: Buyer sees waiting */}
                        {item.status === 'REQUESTED' && isBuyer && (
                            <button className="btn btn-outline" disabled>
                                Waiting for Seller Acceptance...
                            </button>
                        )}

                        {/* ACCEPTED: Both can Chat */}
                        {item.status === 'ACCEPTED' && (isSeller || isBuyer) && (
                            <button className="btn btn-primary" onClick={handleGoToChat}>
                                Go to Chat Room
                            </button>
                        )}

                        {/* COMPLETED: View Receipt */}
                        {item.status === 'COMPLETED' && (
                            <div className="card">
                                <h3>Trade Completed</h3>
                                <p>This item has been sold.</p>
                                <button className="btn btn-outline" style={{ marginTop: '1rem' }}>View Receipt</button>
                            </div>
                        )}
                    </div>

                    <div className={styles.description}>
                        <h3>Description</h3>
                        <p>Rare item found in the deep dungeons. Excellent stats for warriors.</p>
                        {item.options && (
                            <ul style={{ marginTop: '1rem', listStyle: 'inside' }}>
                                {Object.entries(item.options).map(([key, val]) => (
                                    <li key={key}>{key}: {val}</li>
                                ))}
                            </ul>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}
