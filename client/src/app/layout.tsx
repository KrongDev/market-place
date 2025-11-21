import { Inter } from "next/font/google";
import { ReactNode } from "react";
import "./globals.css";
import Header from "@/components/layout/Header";
import Footer from "@/components/layout/Footer";

const inter = Inter({ subsets: ["latin"] });

export const metadata = {
    title: "Market Place",
    description: "Premium Game Item Trading Platform",
};

interface RootLayoutProps {
    children: ReactNode;
}

export default function RootLayout({ children }: RootLayoutProps) {
    return (
        <html lang="en">
            <body className={inter.className}>
                <div style={{ minHeight: '100vh', display: 'flex', flexDirection: 'column' }}>
                    <Header />
                    <main style={{ flex: 1 }}>
                        {children}
                    </main>
                    <Footer />
                </div>
            </body>
        </html>
    );
}
