import CryptoJS from "crypto-js";

const aes = "test1234test1234";
const key = CryptoJS.enc.Utf8.parse(aes);
const iv = CryptoJS.enc.Utf8.parse(aes);
const MOD = {
  iv,
  mode: CryptoJS.mode.CBC,
  padding: CryptoJS.pad.Pkcs7
};

// const data = "21b518ae789e32874a06282a90ef4a6a";

export function decrypt(data = "") {
  if (!data) return "";
  let encryptedHexStr = CryptoJS.enc.Hex.parse(data);
  let srcs = CryptoJS.enc.Base64.stringify(encryptedHexStr);
  let raw = CryptoJS.AES.decrypt(srcs, key, MOD);
  let rawStr = raw.toString(CryptoJS.enc.Utf8);
  if (rawStr.startsWith("{")) {
    return JSON.parse(rawStr);
  } else {
    return rawStr;
  }
}

export function encrypt(raw = "") {
  if (!raw) return "";
  let encrypted = "";
  let e = raw;
  if (typeof raw === "string") {
    const srcs = CryptoJS.enc.Utf8.parse(e);
    encrypted = CryptoJS.AES.encrypt(srcs, key, MOD);
  } else if (typeof raw === "object") {
    e = JSON.stringify(raw);
  }
  const srcs = CryptoJS.enc.Utf8.parse(e);
  encrypted = CryptoJS.AES.encrypt(srcs, key, MOD);
  return encrypted.ciphertext.toString();
}
