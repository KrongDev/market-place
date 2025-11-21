"use client";

import { useEffect, useState } from 'react';
import { useParams } from 'next/navigation';
import { communityService } from '@/services/communityService';
import styles from './detail.module.css';

interface Post {
    id: number;
    title: string;
    content: string;
    authorId: number;
    createdAt: string;
}

export default function PostDetailPage() {
    const { id } = useParams();
    const [post, setPost] = useState<Post | null>(null);

    useEffect(() => {
        const fetchPost = async () => {
            try {
                const data = await communityService.getPostDetail(Number(id));
                setPost(data);
            } catch (e) {
                console.error(e);
            }
        };
        fetchPost();
    }, [id]);

    if (!post) return <div className="container">Loading...</div>;

    return (
        <div className="container" style={{ padding: '4rem 1rem', maxWidth: '800px' }}>
            <div className={styles.postContainer}>
                <h1 className={styles.title}>{post.title}</h1>
                <div className={styles.meta}>
                    <span>By User #{post.authorId}</span>
                    <span>{new Date(post.createdAt).toLocaleString()}</span>
                </div>
                <div className={styles.content}>
                    {post.content}
                </div>
            </div>

            <div className={styles.commentsSection}>
                <h3>Comments</h3>
                <p>Comments functionality coming soon...</p>
            </div>
        </div>
    );
}
