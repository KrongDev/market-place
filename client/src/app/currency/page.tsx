"use client";

import { useEffect, useState } from 'react';
import { currencyService } from '@/services/currencyService';
import Link from 'next/link';
import styles from '@/components/dashboard/ItemGrid.module.css'; // Reuse grid styles

interface CurrencyListing {
    id: number;
    amount: number;
    price: number;
    sellerId: number;
}

export default function CurrencyPage() {
    const [listings, setListings] = useState<CurrencyListing[]>([]);

    useEffect(() => {
        const fetchListings = async () => {
            const data = await currencyService.getAvailableListings();
            setListings(data);
        };
        fetchListings();
    }, []);

    return (
        <div className="container" style={{ padding: '2rem 1rem' }}>
            <h1 className="text-gradient" style={{ marginBottom: '2rem' }}>Currency Exchange</h1>

            <div className={styles.grid}>
                {listings.map((item) => (
                    <Link href={`/currency/${item.id}`} key={item.id} className={styles.card}>
                        <div className={styles.imagePlaceholder} style={{ color: 'gold' }}>
                            💰
                        </div>
                        <div className={styles.cardContent}>
                            <h3 className={styles.itemName}>{item.amount} Gold</h3>
                            <div className={styles.price}>${item.price.toFixed(2)}</div>
                            <div className={styles.seller}>Seller #{item.sellerId}</div>
                        </div>
                    </Link>
                ))}
            </div>
        </div>
    );
}
