import React from 'react';
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Button } from "@/components/ui/button"
import { Separator } from "@/components/ui/separator"
import {Loader2} from "lucide-react";


const MainCard = () => {
    return (
        <div>
            <Card className="w-[400px]">
                <CardHeader>
                    <CardTitle>Shorten your link</CardTitle>
                    <CardDescription>Enter your link and shorten it with one-click</CardDescription>
                </CardHeader>
                <CardContent className="flex flex-col gap-2">
                    <form>
                        <div className="flex flex-col space-y-1.5">
                            <Label htmlFor="link">Paste your link</Label>
                            <Input id="link" placeholder="Link" />
                            <Button className="w-full">Shorten</Button>
                            <Button disabled>
                                <Loader2 className="animate-spin" />
                                Please wait
                            </Button>
                        </div>
                    </form>
                </CardContent>
                <CardFooter  className="flex flex-col justify-between gap-6">
                    <Separator className="w-1/3" />
                    <div className="flex flex-row w-full">
                        <Input type="Link" disabled></Input>
                        <Button variant="secondary"/>
                    </div>
                </CardFooter>
            </Card>
        </div>
    );
};

export default MainCard;