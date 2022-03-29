import plyvel

db = plyvel.DB("C:\\Users\\James\\AppData\\Roaming\\Vampire_Survivors\\Local Storage\\leveldb", create_if_missing=False)
db.put(b'_file://\x00\x01CapacitorStorage.Coins', b'\x0199999')
