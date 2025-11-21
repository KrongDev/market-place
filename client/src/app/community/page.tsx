"use client";

import { useEffect, useState } from 'react';
import { communityService } from '@/services/communityService';
import Link from 'next/link';
import styles from '@/components/dashboard/CommunityHighlights.module.css'; // Reuse styles

interface Post {
    id: number;
    title: string;
    content: string;
    authorId: number;
    createdAt: string;
}

export default function CommunityPage() {
    const [posts, setPosts] = useState<Post[]>([]);

    useEffect(() => {
        const fetchPosts = async () => {
            const data = await communityService.getRecentPosts();
            setPosts(data);
        };
        fetchPosts();
    }, []);

    return (
        <div className="container" style={{ padding: '2rem 1rem' }}>
            <h1 className="text-gradient" style={{ marginBottom: '2rem' }}>Community</h1>

            <div className={styles.list}>
                {posts.map((post) => (
                    <Link href={`/community/${post.id}`} key={post.id} className={styles.card}>
                        <h3 className={styles.postTitle}>{post.title}</h3>
                        <p className={styles.postPreview}>{post.content.substring(0, 100)}...</p>
                        <div className={styles.meta}>
                            <span>By User #{post.authorId}</span>
                            <span>{new Date(post.createdAt).toLocaleDateString()}</span>
                        </div>
                    </Link>
                ))}
            </div>
        </div>
    );
}
