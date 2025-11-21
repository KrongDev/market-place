"use client";

import { useEffect, useState } from 'react';
import { currencyService } from '@/services/currencyService';
import styles from './CurrencyTicker.module.css';
import { TrendingUp, TrendingDown, Minus } from 'lucide-react';

interface CurrencyRate {
    currency: string;
    rate: number;
    trend: 'up' | 'down' | 'stable';
}

export default function CurrencyTicker() {
    const [rates, setRates] = useState<CurrencyRate[]>([]);

    useEffect(() => {
        const fetchRates = async () => {
            const data = await currencyService.getExchangeRates();
            setRates(data);
        };
        fetchRates();
    }, []);

    return (
        <div className={styles.tickerContainer}>
            <div className={styles.tickerTrack}>
                {[...rates, ...rates].map((rate, index) => (
                    <div key={index} className={styles.tickerItem}>
                        <span className={styles.currency}>{rate.currency}</span>
                        <span className={styles.rate}>{rate.rate}</span>
                        {rate.trend === 'up' && <TrendingUp size={14} className={styles.up} />}
                        {rate.trend === 'down' && <TrendingDown size={14} className={styles.down} />}
                        {rate.trend === 'stable' && <Minus size={14} className={styles.stable} />}
                    </div>
                ))}
            </div>
        </div>
    );
}
