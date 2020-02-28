# Spring-Boot-2FA
Example 2FA Google Authenticator - Spring Boot

## Two-factor authentication (2FA)
Two-factor authentication, or 2FA as it's commonly abbreviated, adds an extra step to your basic log-in procedure. Without 2FA, you enter in your username and password, and then you're done. The password is your single factor of authentication. The second factor makes your account more secure, in theory.

Two-factor authentication adds a second level of authentication to an account log-in. When you have to enter only your username and one password, that's considered a single-factor authentication. 2FA requires the user to have two out of three types of credentials before being able to access an account. The three types are:

- Something you know, such as a personal identification number (PIN), password or a pattern
- Something you have, such as an ATM card, phone, or fob
- Something you are, such as a biometric like a fingerprint or voice print

https://www.cnet.com/news/two-factor-authentication-what-you-need-to-know-faq/

## Google Authenticator

![Screenshot](/prtsc/google-authenticator.png)

Google Authenticator is a software-based authenticator by Google that implements two-step verification services using the Time-based One-time Password Algorithm (TOTP; specified in RFC 6238) and HMAC-based One-time Password algorithm (HOTP; specified in RFC 4226), for authenticating users of mobile applications.

When logging into a site supporting Authenticator (including Google services) or using Authenticator-supporting third-party applications such as password managers or file hosting services, Authenticator generates a six- to eight-digit one-time password which users must enter in addition to their usual login details.

https://en.wikipedia.org/wiki/Google_Authenticator

## Example Two-factor authentication (2FA) with Google Authenticator

### Create user

![Screenshot](/prtsc/2fa-1.png)

![Screenshot](/prtsc/2fa-2.png)

![Screenshot](/prtsc/2fa-2.1.png)

![Screenshot](/prtsc/2fa-2.2.png)

### Create QRCode and scan the barcode 

![Screenshot](/prtsc/2fa-3.png)

![Screenshot](/prtsc/2fa-3.1.jpg)

### Validate TOTP key

![Screenshot](/prtsc/2fa-4.png)






