import React, {useEffect} from 'react';
import {useRouter} from 'next/router';

const Redirect = () => {
    const router = useRouter();
    const {shortenedPart} = router.query;

    useEffect(() => {
        if (shortenedPart) {
            window.location.href = `${process.env.NEXT_PUBLIC_API_URL}${shortenedPart}`;
        }
    }, [shortenedPart]);

    return null;
};

export default Redirect;
