import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "UCP - Game Server Dashboard",
  description: "User Control Panel for game server management",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className="antialiased">
        {children}
      </body>
    </html>
  );
}
