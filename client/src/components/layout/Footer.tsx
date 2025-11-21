import styles from './Footer.module.css';

export default function Footer() {
    return (
        <footer className={styles.footer}>
            <div className="container">
                <div className={styles.content}>
                    <div className={styles.brand}>
                        <h3 className="text-gradient">MarketPlace</h3>
                        <p>The premium destination for game item trading.</p>
                    </div>
                    <div className={styles.links}>
                        <h4>Support</h4>
                        <a href="#">Help Center</a>
                        <a href="#">Terms of Service</a>
                        <a href="#">Privacy Policy</a>
                    </div>
                    <div className={styles.social}>
                        <h4>Connect</h4>
                        <a href="#">Twitter</a>
                        <a href="#">Discord</a>
                    </div>
                </div>
                <div className={styles.copyright}>
                    &copy; 2024 MarketPlace. All rights reserved.
                </div>
            </div>
        </footer>
    );
}
