"use client";

import { useEffect, useState, ReactNode } from 'react';
import { useRouter } from 'next/navigation';
import { authService } from '@/services/authService';

interface AdminGuardProps {
    children: ReactNode;
}

export default function AdminGuard({ children }: AdminGuardProps) {
    const router = useRouter();
    const [authorized, setAuthorized] = useState(false);

    useEffect(() => {
        const user = authService.getCurrentUser();
        if (!user) {
            router.push('/login');
        } else if (user.role !== 'ADMIN') {
            alert("Access Denied: Admins only.");
            router.push('/');
        } else {
            setAuthorized(true);
        }
    }, [router]);

    if (!authorized) {
        return <div style={{ padding: '2rem', textAlign: 'center' }}>Checking permissions...</div>;
    }

    return <>{children}</>;
}
