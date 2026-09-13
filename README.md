# TubaTuwa App (WebView Wrapper for tubatuwa.blogspot.com)

এটা একটা সম্পূর্ণ Android Studio প্রজেক্ট, যা আপনার Blogspot সাইটটিকে (`tubatuwa.blogspot.com`) একটা নেটিভ Android অ্যাপের ভেতরে দেখায়। ফিচার:

- আপনার ব্লগ পুরো স্ক্রিনে লোড হয় (কোনো ব্রাউজার address bar নেই)
- নিচে টেনে রিফ্রেশ (pull-to-refresh)
- Android back বাটনে ব্লগের ভেতরের পেজ হিস্টোরিতে ফিরে যায়
- ইন্টারনেট না থাকলে বার্তা দেখায়
- বাইরের লিংক (যেমন বিজ্ঞাপন বা অন্য সাইট) ফোনের আসল ব্রাউজারে খোলে

---

## দ্রুততম উপায়: GitHub দিয়ে ফ্রি APK বানানো (কোনো ইনস্টল লাগবে না)

আপনার কম্পিউটারে Android Studio ইনস্টল করার দরকার নেই — GitHub Actions বিনামূল্যে ক্লাউডে APK বানিয়ে দেবে।

1. [github.com](https://github.com) এ একটা ফ্রি অ্যাকাউন্ট খুলুন (না থাকলে)
2. একটা নতুন **public repository** বানান (যেমন নাম: `tubatuwa-app`)
3. এই পুরো ফোল্ডারের সব ফাইল সেই repo-তে আপলোড করুন (GitHub ওয়েবসাইটে "Add file → Upload files" দিয়েও করা যায়, ড্র্যাগ-ড্রপ করে)
4. আপলোড হয়ে গেলে repo-র ভেতরে **Actions** ট্যাবে যান
5. "Build APK" workflow-টা দেখাবে — সেটাতে ক্লিক করে **Run workflow** চাপুন
6. ২-৩ মিনিট অপেক্ষা করুন, বিল্ড শেষ হলে সবুজ টিক দেখাবে
7. সেই run-এ ক্লিক করে নিচে **Artifacts** সেকশন থেকে `TubaTuwa-debug-apk` ডাউনলোড করুন — এটাই আপনার `.apk` ফাইল (zip আকারে, ভেতরে APK থাকবে)

তারপর:
- ফোনে APK ফাইলটা কপি করুন (Google Drive/Telegram দিয়ে পাঠাতে পারেন)
- ফোনে Settings → Security থেকে "Install from unknown sources" (বা "Install unknown apps") চালু করুন — শুধু একবার
- APK ফাইলে ট্যাপ করে ইনস্টল করুন

---

## বিকল্প: নিজের কম্পিউটারে Android Studio দিয়ে বিল্ড

1. [Android Studio](https://developer.android.com/studio) ইনস্টল করুন (ফ্রি)
2. "Open" দিয়ে এই `TubaTuwaApp` ফোল্ডারটা খুলুন
3. Gradle sync শেষ হওয়া পর্যন্ত অপেক্ষা করুন (প্রথমবার একটু সময় নেয়)
4. উপরে মেনু থেকে **Build → Build Bundle(s)/APK(s) → Build APK(s)**
5. বিল্ড শেষে "locate" লিংকে ক্লিক করলে APK ফাইল পাবেন (`app/build/outputs/apk/debug/app-debug.apk`)
6. সেটা ফোনে কপি করে ইনস্টল করুন

---

## যা কাস্টমাইজ করতে পারেন

- **ভিন্ন সাইট/URL** ব্যবহার করতে চাইলে `MainActivity.kt`-এর `siteUrl` আর `siteHost` ভেরিয়েবল বদলান
- **অ্যাপের নাম** বদলাতে `res/values/strings.xml`-এ `app_name`
- **আইকন** — এখন একটা সাধারণ সবুজ প্লেসহোল্ডার আইকন আছে (`res/drawable/ic_launcher.xml`); নিজের লোগো দিয়ে বদলে নিন
- **Splash screen** যোগ করতে চাইলে বলবেন, আমি কোড দিয়ে দেব

## এরপর কী বাড়াবেন (extend করার priority)

1. **অফলাইন ক্যাশিং** — শেষবার লোড হওয়া পেজ ইন্টারনেট ছাড়া দেখানো (WebView cache mode)
2. **Push notification** — নতুন পোস্ট এলে ফোনে নোটিফিকেশন (Firebase Cloud Messaging লাগবে, Blogspot RSS ফিড চেক করে)
3. **Dark mode toggle** ব্লগের CSS-এর উপর ভিত্তি করে
4. **Play Store-এ পাবলিশ** করতে চাইলে release build সাইন করতে হবে (keystore বানিয়ে) — এটা একটা আলাদা ধাপ, বললে সেটাও দেখিয়ে দেব
