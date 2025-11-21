"use client";

import { useEffect, useState } from 'react';
import { communityService } from '@/services/communityService';
import styles from './CommunityHighlights.module.css';
import { MessageSquare } from 'lucide-react';

interface CommunityPost {
    id: number;
    title: string;
    viewCount: number;
    createdAt: string;
}

export default function CommunityHighlights() {
    const [posts, setPosts] = useState<CommunityPost[]>([]);

    useEffect(() => {
        const fetchPosts = async () => {
            const data = await communityService.getPopularPosts();
            setPosts(data);
        };
        fetchPosts();
    }, []);

    return (
        <section className="container" style={{ paddingBottom: '4rem' }}>
            <h2 className="text-gradient" style={{ marginBottom: '2rem', fontSize: '2rem' }}>Community Hot Topics</h2>
            <div className={styles.list}>
                {posts.map((post) => (
                    <div key={post.id} className={styles.item}>
                        <div className={styles.icon}>
                            <MessageSquare size={24} />
                        </div>
                        <div className={styles.content}>
                            <h3 className={styles.title}>{post.title}</h3>
                            <div className={styles.meta}>
                                <span>Views: {post.viewCount}</span>
                                <span>•</span>
                                <span>{new Date(post.createdAt).toLocaleDateString()}</span>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </section>
    );
}
