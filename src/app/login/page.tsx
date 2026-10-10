"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";

export default function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const router = useRouter();

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setMessage("Logging in...");

    try {
      const response = await fetch("http://localhost:8080/api/users/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });

      const data = await response.text();

      if (response.ok && data.includes("Success")) {
        const token = data.split("Token: ")[1];
        localStorage.setItem("token", token);
        setMessage("✅ Login Successful! Redirecting...");
        
        // ১ সেকেন্ড পর ড্যাশবোর্ডে রিডাইরেক্ট করা হচ্ছে
        setTimeout(() => {
          router.push("/dashboard");
        }, 1000);

      } else {
        setMessage("❌ " + data);
      }
    } catch (error) {
      setMessage("❌ Server error. Is Spring Boot running?");
    }
  };

  return (
    <div className="flex min-h-screen items-center justify-center bg-gray-50">
      <div className="w-full max-w-md rounded-xl bg-white p-8 shadow-lg">
        <h2 className="mb-6 text-center text-3xl font-extrabold text-blue-900">
          Cadre's BCS Compass
        </h2>
        <form onSubmit={handleLogin} className="space-y-5">
          <div>
            <label className="mb-1 block text-sm font-semibold text-gray-700">Email Address</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="w-full rounded-lg border border-gray-300 p-3 focus:border-blue-500 focus:ring-2 focus:ring-blue-200 focus:outline-none transition-all"
              placeholder="admin@cadrescompass.com"
              required
            />
          </div>
          <div>
            <label className="mb-1 block text-sm font-semibold text-gray-700">Password</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full rounded-lg border border-gray-300 p-3 focus:border-blue-500 focus:ring-2 focus:ring-blue-200 focus:outline-none transition-all"
              placeholder="••••••••"
              required
            />
          </div>
          <button
            type="submit"
            className="w-full rounded-lg bg-blue-600 py-3 text-lg font-bold text-white transition-all hover:bg-blue-700 hover:shadow-md"
          >
            Sign In
          </button>
        </form>
        {message && (
          <p className="mt-5 text-center text-sm font-semibold text-gray-800">{message}</p>
        )}
      </div>
    </div>
  );
}
