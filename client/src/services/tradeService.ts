import axios from 'axios';

const API_URL = '/api/v1/trades';

interface TradeItem {
    id: number;
    itemName: string;
    price: number;
    sellerName: string;
    createdAt: string;
    itemImage?: string | null;
}

export const tradeService = {
    getRecentTrades: async (limit: number = 8): Promise<TradeItem[]> => {
        try {
            const response = await axios.get(`${API_URL}?sort=createdAt,desc&size=${limit}`);
            return response.data.data;
        } catch (error) {
            console.error("Failed to fetch trades", error);
            // Return mock data if backend is unavailable
            return Array(limit).fill(0).map((_, i) => ({
                id: i,
                itemName: `Legendary Item ${i + 1}`,
                price: (i + 1) * 1000,
                sellerName: `Seller ${i + 1}`,
                createdAt: new Date().toISOString(),
                itemImage: null // Placeholder will be handled in UI
            }));
        }
    },

    getTradeDetail: async (id: number): Promise<any> => {
        const response = await axios.get(`${API_URL}/${id}`);
        return response.data.data;
    },

    requestTrade: async (id: number, buyerId: number): Promise<void> => {
        await axios.post(`${API_URL}/${id}/request`, null, { params: { buyerId } });
    },

    acceptTrade: async (id: number, sellerId: number): Promise<void> => {
        await axios.post(`${API_URL}/${id}/accept`, null, { params: { sellerId } });
    },

    completeTrade: async (id: number): Promise<void> => {
        await axios.post(`${API_URL}/${id}/complete`);
    }
};
