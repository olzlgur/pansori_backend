# pansori_backend

### Structure
├── gradle  
├── build  
├── gradle/wrapper  
└── src/main/.../weather  
    ├── amazonS3  
    │   ├── FileDetail.java  
    │   ├── FileService.java  
    │   └── MultipartUtil.java  
    ├── api  
    │   ├── AutoController.java  
    │   ├── BookmarkController.java  
    │   ├── CrawlingController.java  
    │   ├── FileController.java  
    │   ├── InquiryController.java  
    │   ├── LitigationController.java  
    │   ├── MailController.java  
    │   ├── MemberController.java  
    │   ├── OpenLawController.java  
    │   ├── PrecedentController.java  
    │   ├── SimplePrecedentController.java  
    │   └── SearchDBController.java  
    ├── config  
    │   ├── RedisConfig.java  
    │   ├── SecurityConfig.java  
    │   ├── SwaggerConfig.java  
    │   └── WebConfig.java  
    ├── domain  
    │   ├── Inquiry  
    │   │   ├── Comment.java  
    │   │   └── Inquiry.java  
    │   ├── Litigation  
    │   │   ├── Court.java  
    │   │   ├── Litigation.java  
    │   │   ├── LitigationStep.java  
    │   │   └── LitigationStepItem.java  
    │   ├── User  
    │   │   ├── Bookmark.java   
    │   │   └── Member.java  
    │   ├── Precedent  
    │   │   ├── Content.java  
    │   │   ├── Detail.Precedent.java  
    │   │   └── SimplePrecedent.java  
    │   ├── Autority.java  
    │   ├── BaseTimeEntity.java  
    │   ├── CommonResponse.java  
    │   ├── InquiryType.java  
    │   ├── Litigation.java  
    │   ├── RefreshToken.java
    │   ├── SearchRecord.java  
    │   └── SearchTable.java  
    │── dto  
    |── elasticsearch  
    |── exception  
    |── jwt  
    |── repostiory  
    |── service   
    |── utils  
    └── PansoriApplication.java  
