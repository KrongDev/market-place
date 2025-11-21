import axios from 'axios';

const API_URL = '/api/v1/posts';

interface CommunityPost {
    id: number;
    title: string;
    content?: string;
    authorId: number;
    viewCount: number;
    createdAt: string;
}

export const communityService = {
    getPopularPosts: async (limit: number = 3): Promise<CommunityPost[]> => {
        try {
            const response = await axios.get(`${API_URL}?sort = viewCount, desc & size=${limit} `);
            return response.data.data;
        } catch (error) {
            console.error("Failed to fetch posts", error);
            // Mock data
            return Array(limit).fill(0).map((_, i) => ({
                id: i,
                title: `Community Discussion Topic ${i + 1} `,
                content: `This is the content for discussion topic ${i + 1}. Lorem ipsum dolor sit amet.`,
                authorId: 1,
                viewCount: 100 + i * 50,
                createdAt: new Date().toISOString()
            }));
        }
    },

    getRecentPosts: async (limit: number = 10): Promise<CommunityPost[]> => {
        try {
            const response = await axios.get(`${API_URL}?sort=createdAt,desc&size=${limit}`);
            return response.data.data;
        } catch (error) {
            console.error("Failed to fetch recent posts", error);
            // Mock data
            return Array(limit).fill(0).map((_, i) => ({
                id: i,
                title: `Recent Post ${i + 1}`,
                content: `This is the content for recent post ${i + 1}. Lorem ipsum dolor sit amet, consectetur adipiscing elit.`,
                authorId: 1,
                viewCount: 50 + i * 10,
                createdAt: new Date().toISOString()
            }));
        }
    },

    getPostDetail: async (id: number): Promise<CommunityPost> => {
        try {
            const response = await axios.get(`${API_URL}/${id}`);
            return response.data.data;
        } catch (error) {
            console.error("Failed to fetch post detail", error);
            // Mock data
            return {
                id,
                title: `Community Post ${id}`,
                content: `This is the detailed content for post ${id}. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.`,
                authorId: 1,
                viewCount: 100,
                createdAt: new Date().toISOString()
            };
        }
    }
};
