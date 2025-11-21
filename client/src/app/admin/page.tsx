"use client";

import { useEffect, useState } from 'react';
import axios from 'axios';
import styles from './admin.module.css';
import AdminGuard from '@/components/auth/AdminGuard';

type TabType = 'users' | 'trades' | 'stats';

interface User {
    id: number;
    username: string;
    email: string;
    role: string;
}

interface Trade {
    id: number;
    item?: {
        name: string;
    };
    price: number;
    sellerId: number;
    buyerId?: number;
    status: string;
}

interface Stat {
    price: number;
}

export default function AdminPage() {
    const [activeTab, setActiveTab] = useState<TabType>('users');
    const [users, setUsers] = useState<User[]>([]);
    const [trades, setTrades] = useState<Trade[]>([]);
    const [stats, setStats] = useState<Stat[]>([]);

    useEffect(() => {
        fetchData();
    }, [activeTab]);

    const fetchData = async () => {
        try {
            if (activeTab === 'users') {
                // Mock Data
                setUsers([
                    { id: 1, username: 'admin', email: 'admin@example.com', role: 'ADMIN' },
                    { id: 2, username: 'user1', email: 'user1@example.com', role: 'USER' },
                    { id: 3, username: 'badguy', email: 'bad@example.com', role: 'USER' },
                ]);
            } else if (activeTab === 'trades') {
                const res = await axios.get('/api/v1/admin/trades');
                setTrades(res.data.data);
            } else if (activeTab === 'stats') {
                const res = await axios.get('/api/v1/admin/stats');
                setStats(res.data.data);
            }
        } catch (e) {
            console.error("Failed to fetch admin data", e);
        }
    };

    const handleBan = async (userId: number) => {
        if (confirm(`Ban user ${userId}?`)) {
            alert(`User ${userId} banned (Mock)`);
        }
    };

    return (
        <AdminGuard>
            <div className={styles.container}>
                <div className={styles.sidebar}>
                    <h2 className={styles.logo}>Admin</h2>
                    <button className={activeTab === 'users' ? styles.active : ''} onClick={() => setActiveTab('users')}>Users</button>
                    <button className={activeTab === 'trades' ? styles.active : ''} onClick={() => setActiveTab('trades')}>Trades</button>
                    <button className={activeTab === 'stats' ? styles.active : ''} onClick={() => setActiveTab('stats')}>Statistics</button>
                </div>

                <div className={styles.content}>
                    <h1 className={styles.header}>{activeTab.charAt(0).toUpperCase() + activeTab.slice(1)} Management</h1>

                    {activeTab === 'users' && (
                        <table className={styles.table}>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Username</th>
                                    <th>Email</th>
                                    <th>Role</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {users.map(u => (
                                    <tr key={u.id}>
                                        <td>{u.id}</td>
                                        <td>{u.username}</td>
                                        <td>{u.email}</td>
                                        <td>{u.role}</td>
                                        <td>
                                            <button className="btn btn-sm btn-danger" onClick={() => handleBan(u.id)}>Ban</button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    )}

                    {activeTab === 'trades' && (
                        <table className={styles.table}>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Item</th>
                                    <th>Price</th>
                                    <th>Seller</th>
                                    <th>Buyer</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                {trades.map(t => (
                                    <tr key={t.id}>
                                        <td>{t.id}</td>
                                        <td>{t.item?.name}</td>
                                        <td>{t.price}</td>
                                        <td>{t.sellerId}</td>
                                        <td>{t.buyerId || '-'}</td>
                                        <td>{t.status}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    )}

                    {activeTab === 'stats' && (
                        <div className={styles.statsGrid}>
                            <div className={styles.statCard}>
                                <h3>Total Transactions</h3>
                                <p className={styles.statValue}>{stats.length}</p>
                            </div>
                            <div className={styles.statCard}>
                                <h3>Total Volume</h3>
                                <p className={styles.statValue}>
                                    {stats.reduce((acc, curr) => acc + curr.price, 0).toLocaleString()} Gold
                                </p>
                            </div>
                        </div>
                    )}
                </div>
            </div>
        </AdminGuard>
    );
}
