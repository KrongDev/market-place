"use client";

import { useEffect, useState } from 'react';
import { tradeService } from '@/services/tradeService';
import Link from 'next/link';
import styles from '@/components/dashboard/ItemGrid.module.css'; // Reuse grid styles

interface TradeItem {
    id: number;
    itemName: string;
    itemImage?: string;
    price: number;
    sellerName: string;
}

export default function ExchangePage() {
    const [items, setItems] = useState<TradeItem[]>([]);

    useEffect(() => {
        const fetchItems = async () => {
            const data = await tradeService.getRecentTrades(20); // Fetch more items
            setItems(data);
        };
        fetchItems();
    }, []);

    return (
        <div className="container" style={{ padding: '2rem 1rem' }}>
            <h1 className="text-gradient" style={{ marginBottom: '2rem' }}>Exchange</h1>

            {/* Simple Filter Placeholder */}
            <div style={{ marginBottom: '2rem', display: 'flex', gap: '1rem' }}>
                <button className="btn btn-outline">All Items</button>
                <button className="btn btn-outline">Weapons</button>
                <button className="btn btn-outline">Armor</button>
            </div>

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
        </div>
    );
}
