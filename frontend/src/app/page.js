import Header from "@/components/Header";
import MainCard from "@/components/MainCard";
import Footer from "@/components/Footer";
import React from "react";

export default function Home() {
  return (
    <div className="flex flex-col justify-between h-screen">
      <Header />
        <div className="flex flex-col justify-center items-center gap-5">
            <div>
                <h1>Welcome to LinkShortenr</h1>
            </div>
            <MainCard />
        </div>
        <Footer />
    </div>
  );
}
