# Help

## ⌚ Add more time zones?

This is not planned.

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
- Extra money costs
- Needs Internet, can go down, needs a caching mechanism
- The API might change and we'd need to keep it up-to-date
- Needs to be translated

### 2. Create my own API service
Pros and cons are same, except being a bit cheaper (you still need to pay).

### 3. Android's time zone provider (this is what NumberHub uses)

read more: https://source.android.com/docs/core/permissions/timezone-rules

**✅Pros:**
- Almost all time zones
- Completely for free
- No extra translations (automatically provided by the system)
- No sudden API changes
- Works offline, never goes down

**❌Cons:**
- Less cities
- Less frequent updates
</details>

## 👩‍⚕️ Body Mass Index

Please note that the values displayed in the app are intended for entertainment purposes only. They can not replace professional medical advice.

Please don't use the Body Mass calculator if you are:
- Under 21 years old
- Pregnant
- Diagnosed with an eating disorder

Please contact your care provider for more information.

## 💵 Wrong Currency Rates?
Currency rates are updated daily. There's no real-time market monitoring on NumberHub.
