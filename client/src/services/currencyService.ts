import axios from 'axios';

const API_URL = '/api/v1/currency';

interface CurrencyRate {
    currency: string;
    rate: number;
    trend: 'up' | 'down' | 'stable';
}

interface CurrencyListing {
    id: number;
    amount: number;
    price: number;
    sellerId: number;
    buyerId?: number;
    status: string;
}

export const currencyService = {
    getExchangeRates: async (): Promise<CurrencyRate[]> => {
        try {
            const response = await axios.get(API_URL);
            return response.data.data;
        } catch (error) {
            console.error("Failed to fetch currency rates", error);
            // Mock data
            return [
                { currency: 'Gold', rate: 100, trend: 'up' },
                { currency: 'Silver', rate: 10, trend: 'down' },
                { currency: 'Copper', rate: 1, trend: 'stable' }
            ];
        }
    },

    getAvailableListings: async (): Promise<CurrencyListing[]> => {
        try {
            const response = await axios.get(`${API_URL}/listings`);
            return response.data.data;
        } catch (error) {
            console.error("Failed to fetch currency listings", error);
            // Mock data
            return Array(6).fill(0).map((_, i) => ({
                id: i,
                amount: (i + 1) * 1000,
                price: (i + 1) * 10,
                sellerId: i + 1,
                status: 'ON_SALE'
            }));
        }
    },

    getListingDetail: async (id: number): Promise<CurrencyListing> => {
        try {
            const response = await axios.get(`${API_URL}/listings/${id}`);
            return response.data.data;
        } catch (error) {
            console.error("Failed to fetch listing detail", error);
            // Mock data
            return {
                id,
                amount: 5000,
                price: 50,
                sellerId: 1,
                status: 'ON_SALE'
            };
        }
    },

    requestListing: async (id: number, buyerId: number): Promise<void> => {
        await axios.post(`${API_URL}/listings/${id}/request`, null, { params: { buyerId } });
    },

    acceptListing: async (id: number, sellerId: number): Promise<void> => {
        await axios.post(`${API_URL}/listings/${id}/accept`, null, { params: { sellerId } });
    }
};
