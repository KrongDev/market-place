import CurrencyTicker from '@/components/dashboard/CurrencyTicker';
import HeroSection from '@/components/dashboard/HeroSection';
import ItemGrid from '@/components/dashboard/ItemGrid';
import CommunityHighlights from '@/components/dashboard/CommunityHighlights';

export default function Home() {
    return (
        <main>
            <CurrencyTicker />
            <HeroSection />
            <ItemGrid />
            <CommunityHighlights />
        </main>
    );
}
