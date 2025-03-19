import "./globals.css";
import {Toaster} from "sonner";

export default function RootLayout({ children }) {
    return (
        <html lang="en" className="dark">
            <body>
                <main>{children}</main>
                <Toaster theme="dark"/>
            </body>
        </html>
    );
}
