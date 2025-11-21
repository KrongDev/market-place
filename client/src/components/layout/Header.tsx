"use client";

import Link from 'next/link';
import { Search, User, Menu, ShoppingBag } from 'lucide-react';
import styles from './Header.module.css';

export default function Header() {
  return (
    <header className={styles.header}>
      <div className={`container ${styles.container}`}>
        <Link href="/" className={styles.logo}>
          <ShoppingBag className={styles.logoIcon} />
          <span className="text-gradient">MarketPlace</span>
        </Link>

        <nav className={styles.nav}>
          <Link href="/exchange" className={styles.navLink}>Exchange</Link>
          <Link href="/currency" className={styles.navLink}>Currency</Link>
          <Link href="/community" className={styles.navLink}>Community</Link>
        </nav>

        <div className={styles.searchBar}>
          <Search size={18} className={styles.searchIcon} />
          <input type="text" placeholder="Search items..." className={styles.searchInput} />
        </div>

        <div className={styles.actions}>
          <Link href="/login" className="btn btn-outline">
            <User size={18} />
            <span>Login</span>
          </Link>
        </div>
      </div>
    </header>
  );
}
