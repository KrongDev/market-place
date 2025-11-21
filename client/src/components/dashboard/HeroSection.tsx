import styles from './HeroSection.module.css';

export default function HeroSection() {
    return (
        <section className={styles.hero}>
            <div className={styles.content}>
                <h1 className={styles.title}>
                    Trade <span className="text-gradient">Legendary</span> Items
                </h1>
                <p className={styles.subtitle}>
                    The ultimate marketplace for gamers. Buy, sell, and exchange items securely.
                </p>
                <div className={styles.actions}>
                    <button className="btn btn-primary">Explore Market</button>
                    <button className="btn btn-outline">Sell Item</button>
                </div>
            </div>
            <div className={styles.glow} />
        </section>
    );
}
