when I compile and sign from Flash Builder I need to use the certificate: SIEMENS.pfx
Passward: Swizzard
and I have to have Timestamp turned on.

Then I have to make backwards compatibility with the old certificate.


adt -migrate -storetype pkcs12 -keystore original_cert.p12 myAppUpdated.air myAppMigrate.air 

adt -migrate -storetype pkcs12 -keystore c:\radua\simple_select\SimpleSelect_old_cert.p12 c:\radua\simple_select\maintanace_versions\1_3_0.air c:\radua\simple_select\maintanace_versions\1_3_0_s.air
password: swizzard



UPDATE: 2015 - now i have to use the certificate SIEMENS.pfx
with passward: swizzard