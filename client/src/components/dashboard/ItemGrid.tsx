"use client";

import { useEffect, useState } from 'react';
import { tradeService } from '@/services/tradeService';
import styles from './ItemGrid.module.css';
import Link from 'next/link';

interface TradeItem {
    id: number;
    itemName: string;
    itemImage?: string;
    price: number;
    sellerName: string;
}

export default function ItemGrid() {
    const [items, setItems] = useState<TradeItem[]>([]);

    useEffect(() => {
        const fetchItems = async () => {
            const data = await tradeService.getRecentTrades();
            setItems(data);
        };
        fetchItems();
    }, []);

    return (
        <section className="container" style={{ padding: '4rem 1rem' }}>
            <h2 className="text-gradient" style={{ marginBottom: '2rem', fontSize: '2rem' }}>Recent Listings</h2>
            <div className={styles.grid}>
                {items.map((item) => (
                    <Link href={`/exchange/${item.id}`} key={item.id} className={styles.card}>
                        <div className={styles.imagePlaceholder}>
                            {item.itemImage ? (
                                <img src={item.itemImage} alt={item.itemName} />
                            ) : (
                                <div className={styles.placeholderIcon}>⚔️</div>
                            )}
                        </div>
                        <div className={styles.cardContent}>
                            <h3 className={styles.itemName}>{item.itemName}</h3>
                            <div className={styles.price}>{item.price.toLocaleString()} Gold</div>
                            <div className={styles.seller}>by {item.sellerName}</div>
                        </div>
                    </Link>
                ))}
            </div>
        </section>
    );
}
