"use client";

import { useEffect, useState, useRef, FormEvent, ChangeEvent } from 'react';
import { useParams, useRouter } from 'next/navigation';
import { chatService } from '@/services/chatService';
import { authService } from '@/services/authService';
import styles from './chat.module.css';
import { Client } from 'stompjs';

interface ChatMessage {
    id: number;
    senderId: number;
    message: string;
    sentAt: string;
}

interface User {
    id: number;
    userId: number;
}

export default function ChatRoomPage() {
    const { roomId } = useParams();
    const router = useRouter();
    const [messages, setMessages] = useState<ChatMessage[]>([]);
    const [input, setInput] = useState('');
    const [currentUser, setCurrentUser] = useState<User | null>(null);
    const stompClientRef = useRef<Client | null>(null);
    const messagesEndRef = useRef<HTMLDivElement | null>(null);

    useEffect(() => {
        const user = authService.getCurrentUser();
        if (!user) {
            router.push('/login');
            return;
        }
        setCurrentUser(user);

        // Load history
        chatService.getMessages(Number(roomId)).then(setMessages);

        // Connect WS
        const stompClient = chatService.connect(Number(roomId), (newMessage: ChatMessage) => {
            setMessages((prev) => [...prev, newMessage]);
            scrollToBottom();
        });
        stompClientRef.current = stompClient;

        return () => {
            if (stompClientRef.current) stompClientRef.current.disconnect();
        };
    }, [roomId, router]);

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
    };

    const handleSend = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (!input.trim() || !stompClientRef.current || !currentUser) return;

        chatService.sendMessage(stompClientRef.current, Number(roomId), currentUser.userId, input);
        setInput('');
    };

    const handleCompleteTrade = async () => {
        if (!currentUser) return;

        if (confirm("Are you sure you want to mark this trade as complete? Both parties must agree.")) {
            try {
                await chatService.completeTrade(Number(roomId), currentUser.userId);
                alert("You have marked the trade as complete. Waiting for partner...");
            } catch (e) {
                alert("Failed to complete trade.");
            }
        }
    };

    return (
        <div className={styles.container}>
            <div className={styles.header}>
                <h2>Chat Room</h2>
                <button className="btn btn-primary" onClick={handleCompleteTrade}>
                    Trade Complete
                </button>
            </div>

            <div className={styles.messages}>
                {messages.map((msg) => {
                    const isMe = String(msg.senderId) === String(currentUser?.userId);
                    return (
                        <div key={msg.id} className={`${styles.message} ${isMe ? styles.me : styles.partner}`}>
                            <div className={styles.bubble}>
                                {msg.message}
                            </div>
                            <div className={styles.time}>
                                {new Date(msg.sentAt).toLocaleTimeString()}
                            </div>
                        </div>
                    );
                })}
                <div ref={messagesEndRef} />
            </div>

            <form onSubmit={handleSend} className={styles.inputArea}>
                <input
                    type="text"
                    value={input}
                    onChange={(e: ChangeEvent<HTMLInputElement>) => setInput(e.target.value)}
                    placeholder="Type a message..."
                    className={styles.input}
                />
                <button type="submit" className="btn btn-primary">Send</button>
            </form>
        </div>
    );
}
