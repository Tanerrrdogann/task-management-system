# Akıllı Görev Yönetimi ve Hatırlatma Sistemi

##    Proje Özeti

Bu proje, kullanıcıların görevlerini yönetmesini, öncelikli görevlerde otomatik e-posta hatırlatması almasını ve günlük/haftalık performanslarını PDF raporu olarak almasını sağlayan bir akıllı görev yönetim sistemidir.

##    Kullanılan Teknolojiler

- Java 17
- Spring Boot
- Thymeleaf
- Spring Web / MVC
- OpenFeign (Dış API tüketimi için)
- JavaMailSender (E-posta bildirimi için)
- iTextPDF (PDF oluşturmak için)
- JUnit 5 (Testler için)
- Maven (Build ve bağımlılık yönetimi)

##    Kurulum ve Çalıştırma

1. Proje klasörüne gidin:
    ```bash
    cd akilli-gorev
    ```

2. E-posta hatırlatmaları için ortam değişkenlerini ayarlayın. E-posta kullanmayacaksanız bu adımı boş bırakabilirsiniz:
    ```bash
    export MAIL_USERNAME="your-email@gmail.com"
    export MAIL_PASSWORD="your-app-password"
    ```
   İsteğe bağlı olarak `MAIL_HOST` ve `MAIL_PORT` değerleri de ortam değişkeni olarak verilebilir.

3. Maven ile testleri çalıştırın:
    ```bash
    mvn test
    ```

4. Maven ile projeyi build edin:
    ```bash
    mvn clean package
    ```

5. Uygulamayı başlatın:
    ```bash
    mvn spring-boot:run
    ```

6. Uygulamaya şu adresten erişebilirsiniz:
    - [http://localhost:8080](http://localhost:8080)

> Not: Uygulama ilk açılışta ekran görüntüsü ve demo kullanım için birkaç örnek görev oluşturur. Bu davranışı kapatmak için `DEMO_DATA_ENABLED=false` ortam değişkenini kullanabilirsiniz.

##    Özellikler

- Görev ekleme, listeleme, güncelleme, silme (CRUD)
- Öncelikli görevlerde otomatik e-posta gönderimi
- Günlük/haftalık PDF performans raporu
- Motivasyon sözü çekme (dış API ile)
- GUI tabanlı kullanım (Thymeleaf)
- Loglama (log.txt dosyasına önemli işlemler yazılır)

##    API Uç Noktaları

| HTTP | URL | Açıklama |
|------|-----|----------|
| GET | `/api/tasks` | Tüm görevleri getirir |
| POST | `/api/tasks` | Yeni görev ekler |
| PUT | `/api/tasks/{id}/complete` | Görevi tamamlar |
| DELETE | `/api/tasks/{id}` | Görevi siler |
| GET | `/api/quote/motivational` | Motivasyon sözü getirir |
| GET | `/api/pdf/report` | Görev PDF raporu oluşturur |

##    Geliştirici Notları

- GUI arayüzde tamamla, sil, PDF al butonları işlevseldir.
- E-posta özelliği test ortamında mock ya da gerçek SMTP üzerinden kullanılabilir.
- Dış API'den veri almak için OpenFeign kullanılmıştır.

##    Dizin Yapısı

- `src/main/java`: Uygulama ana kaynak kodları
- `src/main/resources/templates`: Thymeleaf HTML dosyaları
- `src/main/resources/static/css`: CSS stilleri
- `src/test`: JUnit testleri
- `documents`: Kullanım kılavuzu ve test senaryoları
- `reports`: Örnek rapor dosyaları (PDF)
- `postman`: Postman API koleksiyonu

##    Geliştiriciler

- İsmail Taner Erdoğan
- Nisa Gökşen
