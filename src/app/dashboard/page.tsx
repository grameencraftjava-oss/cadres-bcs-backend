"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";

export default function DashboardPage() {
  const [token, setToken] = useState("");
  const router = useRouter();

  useEffect(() => {
    // লোকাল স্টোরেজ থেকে টোকেন খোঁজা হচ্ছে
    const storedToken = localStorage.getItem("token");
    if (!storedToken) {
      router.push("/login"); // টোকেন না পেলে লগইনে কিক আউট করবে
    } else {
      setToken(storedToken);
    }
  }, [router]);

  const handleLogout = () => {
    localStorage.removeItem("token");
    router.push("/login"); // লগআউট করলে আবার লগইন পেজে যাবে
  };

  return (
    <div className="min-h-screen bg-gray-50 p-10">
      <div className="max-w-4xl mx-auto bg-white p-8 rounded-xl shadow-md border-t-4 border-blue-600">
        <h1 className="text-3xl font-extrabold text-blue-900 mb-4">Student / Admin Dashboard</h1>
        <p className="text-gray-600 mb-6">Welcome to Cadre's BCS Compass! You are successfully logged in.</p>

        <div className="bg-green-50 border border-green-200 p-4 rounded-lg mb-6 break-all">
          <span className="font-bold text-green-800">Your Active JWT Token:</span>
          <p className="text-green-700 font-mono text-sm mt-2">{token}</p>
        </div>

        <button
          onClick={handleLogout}
          className="bg-red-500 text-white px-6 py-2 rounded-lg font-bold hover:bg-red-600 transition-all"
        >
          Logout
        </button>
      </div>
    </div>
  );
}
