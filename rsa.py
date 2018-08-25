# -- coding=utf-8 --
from Crypto.Signature import PKCS1_v1_5 as sign_pkcs1
from Crypto.PublicKey import RSA
from Crypto.Hash import SHA256

message ="胡斐封疆大吏分"
private_file_path = 'private_key.pem'
public_file_path = 'public_key.pem'
java_sign_path = 'sign.txt'

def sign():
    key = open(private_file_path, 'r').read()
    rsakey = RSA.importKey(key)
    signer = sign_pkcs1.new(rsakey)
    digest = SHA256.new()
    digest.update(message)
    sign = signer.sign(digest)
    sign_hex = sign.encode('hex')
    return sign_hex

def checksign(sign_hex):
    key = open(public_file_path, 'r').read()
    rsakey = RSA.importKey(key)
    verifier = sign_pkcs1.new(rsakey)
    digest = SHA256.new()
    digest.update(message)
    print verifier.verify(digest, sign_hex.decode('hex'))

if __name__ == '__main__':
    sign_hex = sign()
    print(sign_hex)
    java_sign = open(java_sign_path).read()
    print(sign_hex == java_sign)
    checksign(sign_hex)
