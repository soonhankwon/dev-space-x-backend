## 프로젝트 소개

---

<aside>
  
> 🔭 **Dev SpaceX는 개발 분야의 특정 주제**에 대해 **컨텐츠를 연재 및 구독, 리뷰 할 수 있는 API** 입니다.

</aside>

## 핵심 기능

---

### 👨‍👩‍👦‍👦 유저

모든 유저는 **컨텐츠 및 시리즈를 연재 및 소비**할 수 있습니다.

### 📝 컨텐츠

개발 관련 주제의 **컨텐츠**입니다.

### 📚 시리즈

개발 관련 주제의 **시리즈**로 컨텐츠를 연재합니다.

### 💵 다크매터

스페이스에서 재화는 **Dark Matter** 입니다.

### 📱 카테고리

컨텐츠 주제에 대한 **카테고리** 입니다.

### 👍🏽 리뷰

컨텐츠에 대한 **피드백**을 통한 신뢰성있는 컨텐츠 제공을 목표로 합니다.

- [Dev SpaceX 의 자세한 기능 링크 CLICK](https://www.notion.so/Dev-SpaceX-Backend-101f2e54082a4d71949fbd1bab0877b0)

---

## TECK STACK

---

- Java 11
- SpringBoot 2.7.13
- Spring Data JPA
- Querydsl 5.0.0
- JUnit5
- Swagger 3.0
- MySQL 8.0.31
- H2 database
- Redis 7.0.8
- Redisson 3.21.1
- Apache Jmeter

---

## DB 테이블 정의서

---

### ERD
<details>
<summary><strong> Diagram </strong></summary>
<div markdown="1">       

![dev-space-erd](https://github.com/soonhankwon/coffee-plz-backend/assets/113872320/3c8b524e-783d-4b3d-8064-954cc53621c2)

</div>
</details>

### DDL
<details>
<summary><strong> DDL </strong></summary>
<div markdown="1">       

  ```sql
    CREATE SCHEMA IF NOT EXISTS `dev-space-db` DEFAULT CHARACTER SET utf8 ;
    USE `dev-space-db` ;
    
    CREATE TABLE IF NOT EXISTS `dev-space-db`.`user` (
      `id` BIGINT NOT NULL,
    	`email` VARCHAR(255) NULL,
      `name` VARCHAR(255) NULL,
      `password` VARCHAR(255) NULL,
      `user_type` VARCHAR(45) NULL,
      `dark_matter` BIGINT NULL,
      `exp` BIGINT NULL,
      PRIMARY KEY (`id`))
    	INDEX `idx_user_email` (`email ASC));
    
    CREATE TABLE IF NOT EXISTS `dev-space-db`.`series` (
      `id` BIGINT NOT NULL,
      `name` VARCHAR(255) NULL,
      `status` VARCHAR(45) NULL,
      `type` VARCHAR(45) NULL,
      `category` VARCHAR(45) NULL,
      `user_id` BIGINT NOT NULL,
      PRIMARY KEY (`id`));
    
    CREATE TABLE IF NOT EXISTS `dev-space-db`.`content` (
      `id` INT NOT NULL,
      `title` VARCHAR(255) NULL,
      `text` TEXT NULL,
      `type` VARCHAR(45) NULL,
      `dark_matter` BIGINT NULL,
      `created_at` TIMESTAMP NULL,
      `modified_at` TIMESTAMP NULL,
      `category` VARCHAR(45) NULL,
      `series_id` BIGINT NOT NULL,
      PRIMARY KEY (`id`),
    	INDEX `fx_content_series_idx` (`series_id ASC)
    	INDEX `idx_content_category_idx` (`category ASC));
    
    CREATE TABLE IF NOT EXISTS `dev-space-db`.`user_content` (
      `id` BIGINT NOT NULL,
      `type` VARCHAR(45) NULL,
      `created_at` TIMESTAMP NULL,
      `modified_at` TIMESTAMP NULL,
      `user_id` BIGINT NOT NULL,
      `content_id` INT NOT NULL,
      PRIMARY KEY (`id`),
    	INDEX `fx_user_content_user_idx` (`user_id ASC),
      INDEX `fx_user_content_content_idx` (`content_id ASC),
    	INDEX `idx_content_content_type_idx` (`type ASC));
    
    CREATE TABLE IF NOT EXISTS `dev-space-db`.`dark_matter_history` (
      `id` INT NOT NULL,
      `type` VARCHAR(45) NULL,
      `dark_matter` BIGINT NULL,
      `created_at` TIMESTAMP NULL,
      `user_id` BIGINT NOT NULL,
      PRIMARY KEY (`id`));
    
    CREATE TABLE IF NOT EXISTS `dev-space-db`.`review` (
      `id` INT NOT NULL,
      `type` VARCHAR(45) NULL,
      `comment` VARCHAR(45) NULL,
      `user_id` BIGINT NOT NULL,
      `content_id` INT NOT NULL,
      PRIMARY KEY (`id`));
  ```

</div>
</details>

## API

---

- [Swagger API 명세서](http://localhost:8080/swagger-ui/index.html)
  
    - 프로젝트 **애플리케이션 RUN** 후 링크를 클릭하면 확인 가능합니다.
      
- Redis Server
    - **Redis** 사용으로 redis-server 를 구동해야 Redis 적용 기능이 정상 동작합니다.

## API TEST

---

### Swagger UI

- 위의 **스웨거 명세서 링크**를 통해 간단한 API 테스트가 가능합니다.

### Test Code

API 테스트 검증 및 자동화된 테스트를 위해 /test 경로에 **테스트 코드**를 작성했습니다.

- **도메인 단위 테스트 (Unit Test)**
  
    - 대부분의 비즈니스 로직은 도메인 클래스 안에서 이루어지도록 **캡슐화**했습니다.
    - 모든 메서드의 **단위 테스트**를 진행합니다.
    - 경계범위값의 예외처리 테스트 코드 또한 작성되있습니다.
      
- **레포지토리 테스트 (DataJpaTest)**
    - 레포지토리 쿼리관련 메서드에 대한 검증을 진행합니다.
- **서비스 로직**  **테스트**
    - Mockito 를 사용해서 서비스의 비즈니스 로직 검증을 진행합니다.
- **통합 테스트**
    - 스프링부트 통합 테스트를 통해 API 사용시 테스트할 수 있는 시나리오들을 코드로 짜서 자동화된 API 테스트를 작성하고 있습니다.

## 핵심 문제해결 전략 및 내용

---

### 성능 최적화

- **100만건의 컨텐츠와 100만건의 유저 컨텐츠** **(유저 컨텐츠 포스팅 이력)**
  
- 10만명의 유저
- 위 더미 데이터로 **프로토 모델을 테스팅**한 결과 많은 API 에서 목표한 **Latency(0.5sec)** 를 충족하지 못하는 문제가 발생했습니다.

### 인덱싱, 반정규화, Redis 캐싱을 통한 성능 개선

- [자세한 성능 개선 & 전략 LOG CLICK](https://www.notion.so/LOG-6be51dcd9b1e47068ecf4cc8ae867850?pvs=21)
  
- 로그인 API
  
    - **인덱싱 : TPS 227% 개선, Latency 0.246 sec**
- 전체 컨텐츠 목록 조회 API
    - 인덱싱 + 반정규화 : **TPS 144% 개선, Latency 0.389 sec**
- 컨텐츠 상세 조회 API
    - Event + 인덱싱 + 반정규화 : **TPS 7850% 개선, Latency 0.031 sec**
- 시리즈 컨텐츠 목록 조회 API
    - 인덱싱 + 반정규화 : **TPS 2808% 개선, Latency 0.025 sec**
- 특정 컨텐츠 사용자 이력 조회 API
    - 인덱싱 :  **TPS 2750% 개선, Latency 0.029 sec**
- 회원이 가지고 있는 컨텐츠 조회 API
    - 인덱싱 : **TPS 1385% 개선, Latency 0.014 sec**
- 리뷰 좋아요 또는 아쉬워요 TOP3 컨텐츠 조회 API
    - Redis 캐싱 : **Latency 233% 개선, Latency 0.006 sec**

---

### 컨텐츠 상세 조회와 조회 이력 저장 로직의 결합도 개선

**유저는 컨텐츠의 빠른 로딩을 기대합니다.**

- 하지만 컨텐츠 상세 조회와 조회 이력 저장이 **하나의 트랜잭션 (Transaction)** 으로 묶여있습니다.
  
- **조회 이력 저장에 장애**가 생길 경우 컨텐츠의 빠른 로딩을 보장할 수 없습니다.
    - 하지만, **유료 컨텐츠가 조회**시 이전 조회 이력에 의거해서 컨텐츠를 상세 조회합니다.
    - 조회이력이 제대로 저장되지 않았다면, 데이터 정합성은 물론 다크매터가 중복해서 빠져나가는 심각한 문제를 초래합니다.
    - 결론적으로, 상세 조회시 **조회 이력은 꼭 저장해야하는 중요한 정보**입니다.
- **이미 조회이력이 있는 경우만 비동기로  조회 이력을 저장하면 어떨까?**
    - **조회 이력이 있는 경우**는 컨텐츠 상세 조회시 다크 매터가 필요하지 않습니다.
    - 따라서 상대적으로 이 경우 조회 이력 저장의 중요도는 떨어집니다.
- 따라서 해당 경우만 **이벤트를 사용**, 로직을 분리시켜 **결합도를 개선**시켰습니다.
    - ApplicationEventPublisher : **이미 조회 이력이 있는 경우**, 조회 이벤트가 발생하면 작동합니다.
    - **TransactionEventListener** 의  **AfterCommit을 사용**해서 **조회 트랜잭션이** **커밋된 경우 이력 저장 메서드를 실행**합니다.
- Jmeter 테스트 시 **TPS 21.2% Latency 23.5%** 특히 **Max Latency** 가 **348%** 개선되었습니다.

---

### 유저 리뷰 좋아요 또는 아쉬워요 TOP3 컨텐츠 조회 API

**ReviewType**

- 좋아요, 아쉬워요는 열거형 클래스 **ReviewType** 으로 정의했습니다.

**QueryDsl**

기존 JPA 사용시 메서드 이름이 길고 복잡해지며 **동적쿼리가 힘들다는 단점**이 발생했습니다.

- **QueryDsl**을 사용, ReviewType(LIKE or DISLIKE)를 매개변수로 받아 **동적 쿼리를 사용**해서 개선했습니다.
    - 좋아요 또는 아쉬워요 케이스 별로 탑3 컨텐츠를 반환합니다.
- CODE
    
    ```java
    @RequiredArgsConstructor
    @Repository
    public class ContentRepositoryImpl implements ContentRepositoryCustom{
    
        private final JPAQueryFactory queryFactory;
    
        QReview review = QReview.review;
        QContent content = QContent.content;
    
        @Override
        public List<Content> findTop3ContentsByReviewType(ReviewType type) {
            return queryFactory.select(content)
                    .from(content)
                    .join(review).on(content.id.eq(review.content.id))
                    .where(typeEq(type))
                    .groupBy(content)
                    .orderBy(review.count().desc())
                    .limit(3)
                    .fetch();
        }
    
        private BooleanExpression typeEq(ReviewType type) {
            return type == null ? null : review.type.eq(type);
        }
    }
    ```
    
- **아쉬워요 TOP3 기능**은 관리자만 사용가능하도록 로그인 유저 권한 체크를 합니다.
- CODE
    
    ```java
    @GetMapping("/top3")
    @Operation(summary = "리뷰 좋아요 또는 아쉬워요 TOP3 컨텐츠 조회 API", description = "아쉬워요 TOP3 는 관리자용")
    @ResponseStatus(HttpStatus.OK)
    public List<ContentGetResDto> getTop3Contents(@RequestParam("type") ReviewType type, HttpServletRequest request) {
        if (type != ReviewType.LIKE) {
            User loginUser = sessionServiceImpl.getLoginUserBySession(request);
            if (!loginUser.isTypeAdmin()) {
                throw new ApiException(CustomErrorCode.NO_AUTH_TO_ACCESS_API);
            }
            return contentServiceImpl.getTop3DisLikedContents();
        }
        return contentServiceImpl.getTop3LikedContents();
    }
    ```
    

**Redis - Cache**

- 실시간 랭킹 순위가 아니므로, 해당 **API 응답결과를 주기적으로 캐싱**해서 보여주면 서버 비용을 줄일 수 있을 것이라고 예상해서 사용했습니다.
  
- In-memory DB 인 **Redis** 를 적용했습니다.
- **@Cacheable** 애노테이션을 사용해서 좋아요, 아쉬워요 컨텐츠 랭킹을 **캐싱하**여 캐싱된 데이터를 조회하도록 합니다.
    - Redis 서버가 다운되거나, 캐시가 없어졌을 경우 같은 연산을 하여 조회 정보를 리턴하고, 결과를 다시 캐싱합니다.
- **스케쥴**을 활용하여 **매일 밤 12시** 좋아요와 아쉬워요 랭킹을 각각 업데이트하고 Redis 에 캐시를 저장합니다.

---

### 동시성 제어 이슈

**컨텐츠 등록**시 **동시에 같은 요청** 을 했을 경우 **요청수 만큼 컨텐츠가 등록되는 문제** 발생하는것을 확인했습니다.

- **Reddison 분산락**을 사용해서 **락을 점유한 요청만 커밋되도록 적용**했습니다.
- **락을 점유한 요청만 트랜잭션이 적용**되도록 함수형 인터페이스 **Supplier** 를 사용해 트랜잭션 적용

---

### 유효성 검사 (Validation)

**Request** **DTO - @Validated**

- 애플리케이션에서 가장 중요한 점의 하나인 **유효성 검사**에 대해 개인적으로 고민이 많았던 이슈가 있었습니다.
- [Validation 에 대한 고민 링크](https://www.notion.so/Validation-fabb90a695bf444a903e525ed486c80d?pvs=21)
- 모든 Request DTO 에 **Validated** 를 적용해서 **유효성 검사**를 진행 및 테스트했습니다.

---

### 카테고리 반정규화

- 기존에는 카테고리 테이블을 따로 만들어 컨텐츠와 시리즈에 **OneToOne** 연관관계를 맺어주었습니다.
    - 관계가 많아진 만큼 **컨텐츠와 시리즈에 Join 쿼리**가 많아지고 성능 저하의 원인으로 예상했습니다.
    - 카테고리에 따로 DB에서 관리할 데이터가 없는 현재 상황에 DB 에 따로 불필요한 테이블을 만들 필요가 없다고 생각했습니다.
- **카테고리를 Enum 클래스로 리팩토링** 했습니다.
    - CODE
        
        ```java
        @AllArgsConstructor
        @Getter
        public enum Category {
        
            JAVA,
            SPRING,
            DATABASE,
            ALGORITHM,
            ORM,
            CS,
            BACKEND,
            REDIS,
            DOCKER,
            FRONTEND,
            JAVASCRIPT
        }
        ```
        
- **반정규화**를 통해 컨텐츠와 시리즈에 **category 컬럼(varchar)**을 만들어주었습니다.
- 해당 과정을 통해 테스트 코드 및 프로덕션 코드에서 카테고리를 세팅해주던 코드들이 모두 사라져 **복잡도가 줄어들었습니다**. 또한 불필요한 레포지토리 조회도 사라졌습니다.
- 성능상으로는 Join 쿼리 및 Where 절의 카테고리 미사용으로 **조회 성능이 전체적으로 상당히 개선**되었습니다.

---

### 테스트용 DB 분리 & 구축

- 테스트 코드 실행시 **독립적인 DB 사용 및 인메모리 DB(H2)의 필요성**을 느꼈습니다.
  
    - **프로덕션 DB 의 데이터가 100만건 이상 → 속도 문제 + 위험성**
    - **테스트 속도 향상 및 위험성 제거**
- H2Database 의존성을 추가해주었습니다.
- /test 루트에 [application.properties](http://application.properties) 에 테스트용 서버 설정 (H2)을해주어 따로 구성해주었습니다.

## ISSUE 및 LOG

---

[ISSUE](https://www.notion.so/ISSUE-0ebfced427064f10824f14354862e0a5?pvs=21)

[성능 테스트 시나리오](https://www.notion.so/476ce57f3b114d6bafafd2a1cbd3c425?pvs=21)

[성능 개선 LOG](https://www.notion.so/LOG-6be51dcd9b1e47068ecf4cc8ae867850?pvs=21)
