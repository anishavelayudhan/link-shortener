"use client"

import React, {useState} from "react";
import {Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle} from "@/components/ui/card"
import {toast} from "sonner"
import {Input} from "@/components/ui/input"
import {Label} from "@/components/ui/label"
import {Button} from "@/components/ui/button"
import {Separator} from "@/components/ui/separator"
import {Loader2, Copy} from "lucide-react";
import axios from "axios";


const MainCard = () => {
    const [linkData, setLinkData] = useState({longUrl: ""})
    const [shortenedUrl, setShortenedUrl] = useState("")
    const [loading, setLoading] = useState(false);
    const [showUrl, setShowUrl] = useState(false);
    const baseUrl = 'http://localhost:8080/'
    const apiUrl = 'http://localhost:8080/url/'

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);

        try {
            const response = await axios.post(apiUrl, linkData);
            const shortUrl = response.data.shortUrl;
            setShortenedUrl(shortUrl);
            copyToClipboard(baseUrl + shortUrl);
        } catch (err) {
            toast("Uh oh! Something went wrong.", {
                description: "Try again with a valid URL",
            })
        } finally {
            setLoading(false);
            setShowUrl(true);
        }
    };

    const handleChange = (e) => {
        setLinkData({longUrl: e.target.value});
    }

    const copyToClipboard = (shortenedLink) => {
        navigator.clipboard.writeText(shortenedLink).then(() => {
            toast("Copied to clipboard!", {
                description: shortenedLink
            })
        }).catch((err) => {
            toast("Uh oh! Something went wrong.", {
                description: "There was a problem copying the clipboard.",
            })
            console.log(err);
        });
    };

    return (
        <div>
            <Card className="w-[400px]">
                <CardHeader>
                    <CardTitle>Shorten your link</CardTitle>
                    <CardDescription>Enter your link and shorten it with one-click</CardDescription>
                </CardHeader>
                <CardContent className="flex flex-col gap-2">
                    <form onSubmit={handleSubmit}>
                        <div className="flex flex-col space-y-1.5">
                            <Label htmlFor="link">Paste your link</Label>
                            <Input id="link" placeholder="Link" onChange={handleChange}/>
                            <Button className="w-full" type="submit" disabled={loading}>
                                {loading ?
                                    (<>
                                        <Loader2 className="animate-spin"/>
                                        Please wait
                                    </>)
                                    : ('Shorten')}
                            </Button>
                        </div>
                    </form>
                </CardContent>
                {showUrl &&
                    <CardFooter className="flex flex-col justify-between gap-6">
                        <Separator className="w-1/3"/>
                        <div className="flex flex-row w-full gap-1">
                            <Input
                                type="text"
                                value={shortenedUrl ? `${baseUrl}${shortenedUrl}` : ''}
                                readOnly
                                className="text-muted-foreground border-muted-foreground-dark"
                            ></Input>
                            <Button variant="secondary" size="icon" onClick={() => copyToClipboard(baseUrl + shortenedUrl)}>
                                <Copy />
                            </Button>
                        </div>
                    </CardFooter>
                }
            </Card>
        </div>
    );
};

export default MainCard;