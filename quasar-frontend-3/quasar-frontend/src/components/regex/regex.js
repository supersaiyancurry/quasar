export const isValidEmail = (email) => /[\w-]+@([\w-]+\.)+[\w-]+/.test(email);
export const isValidName = (name) => name.trim() !== '';
export const isValidPassword = (password) => /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/.test(password);
export const isValidState = (state) => /^(?:(A[KLRZ]|C[AOT]|D[CE]|FL|GA|HI|I[ADLN]|K[SY]|LA|M[ADEINOST]|N[CDEHJMVY]|O[HKR]|P[AR]|RI|S[CD]|T[NX]|UT|V[AIT]|W[AIVY]))$/.test(state);
export const isValidZipCode = (zipCode) => /(^\d{5}$)|(^\d{5}-\d{4}$)/.test(zipCode);
export const isValidPhone = (phone) => /^\D?(\d{3})\D?\D?(\d{3})\D?(\d{4})$/.test(phone);
