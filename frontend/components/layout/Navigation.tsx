'use client';

import Link from 'next/link';
import { useRouter, usePathname } from 'next/navigation';
import { clearAuth, getUsername, getRole, isAdmin } from '@/lib/auth';

export default function Navigation() {
  const router = useRouter();
  const pathname = usePathname();
  const username = getUsername();
  const role = getRole();
  const showAdminLinks = isAdmin();

  const handleLogout = () => {
    clearAuth();
    router.push('/login');
  };

  const navLinks = [
    { href: '/dashboard', label: 'Dashboard' },
  ];

  if (showAdminLinks) {
    navLinks.push({ href: '/admin', label: 'Admin Panel' });
  }

  return (
    <nav className="bg-gray-800 text-white shadow-lg">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16">
          <div className="flex">
            <div className="flex-shrink-0 flex items-center">
              <h1 className="text-xl font-bold">UCP</h1>
            </div>
            <div className="ml-6 flex space-x-8">
              {navLinks.map((link) => (
                <Link
                  key={link.href}
                  href={link.href}
                  className={`inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium ${
                    pathname === link.href
                      ? 'border-indigo-500 text-white'
                      : 'border-transparent text-gray-300 hover:border-gray-300 hover:text-white'
                  }`}
                >
                  {link.label}
                </Link>
              ))}
            </div>
          </div>
          <div className="flex items-center">
            <span className="text-sm text-gray-300 mr-4">
              {username} <span className="text-xs text-gray-400">({role})</span>
            </span>
            <button
              onClick={handleLogout}
              className="bg-red-600 hover:bg-red-700 px-4 py-2 rounded-md text-sm font-medium"
            >
              Logout
            </button>
          </div>
        </div>
      </div>
    </nav>
  );
}
