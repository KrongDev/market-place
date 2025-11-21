import axios from 'axios';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

const API_URL = '/api/v1/chat/rooms';
const WS_URL = 'http://localhost:8084/ws-chat'; // Direct to Chat Service or via Proxy

interface ChatRoom {
    roomId: number;
    tradeId: number;
    sellerId: number;
    buyerId: number;
}

interface ChatMessage {
    id: number;
    roomId: number;
    senderId: number;
    message: string;
    timestamp: string;
}

export const chatService = {
    createRoom: async (tradeId: number, sellerId: number, buyerId: number): Promise<number> => {
        const response = await axios.post(API_URL, { tradeId, sellerId, buyerId });
        return response.data.data; // Returns roomId
    },

    getMyRooms: async (userId: number): Promise<ChatRoom[]> => {
        const response = await axios.get(API_URL, { params: { userId } });
        return response.data.data;
    },

    getMessages: async (roomId: number): Promise<ChatMessage[]> => {
        const response = await axios.get(`${API_URL}/${roomId}/messages`);
        return response.data.data;
    },

    completeTrade: async (roomId: number, userId: number): Promise<void> => {
        await axios.post(`${API_URL}/${roomId}/complete`, null, { params: { userId } });
    },

    connect: (roomId: number, onMessageReceived: (message: ChatMessage) => void): Stomp.Client => {
        const socket = new SockJS(WS_URL);
        const stompClient = Stomp.over(socket);

        stompClient.connect({}, () => {
            stompClient.subscribe(`/topic/room/${roomId}`, (payload) => {
                const message = JSON.parse(payload.body);
                onMessageReceived(message);
            });
        }, (err: any) => {
            console.error("WebSocket Connection Error:", err);
        });

        return stompClient;
    },

    sendMessage: (stompClient: Stomp.Client, roomId: number, senderId: number, message: string): void => {
        if (stompClient && stompClient.connected) {
            stompClient.send("/app/chat.sendMessage", {}, JSON.stringify({
                roomId,
                senderId,
                message
            }));
        }
    }
};
