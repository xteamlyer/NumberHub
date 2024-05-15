# Help

## ⌚ Add more time zones?

No.

<details>
<summary>But... why?</summary>

Each time zone has its' own set of rules:
- Offset from GMT
- Daylight saving time offset
- Date when daylight saving time is used

This data always changes.
There are 3 solutions:

### 1. Paid API service

**✅Pros:**
- Almost all time zones and cities
- Always up-to-date

**❌Cons:**
- Costs money
- Needs Internet, can go down, needs a caching mechanism
- Can change API and break Unitto
- Needs to be translated

### 2. Create my own API service
Pros and cons are same, except this one is free cheaper (you still need to pay).

### 3. Android's time zone provider (this is what Unitto uses)

read more: https://source.android.com/docs/core/permissions/timezone-rules

**✅Pros:**
- Almost all time zones
- Free
- No need to translate (provided by system)
- Doesn't break Unitto
- Works offline, never goes down

**❌Cons:**
- Less cities
- Less frequent updates (or none if you are unlucky)
</details>

## 👩‍⚕️ Body Mass Index

Please note that the values displayed in the app are intended for entertainment purposes only. They can not replace professional medical advice. Please don't use Body Mass calculator if you are:
- Under 21
- Pregnant
- Diagnosed with an eating disorder

Please contact your care provider for more information.

## 💵 Wrong Currency Rates?
Currency rates are updated daily. There's no real-time market monitoring in the app.