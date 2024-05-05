<h1 align="center">ĐỒ ÁN TỐT NGHIỆP</h1>
 <h1 align="center">TRƯỜNG ĐẠI HỌC MỞ HÀ NỘI </br>
KHOA CÔNG NGHỆ THÔNG TIN</h1>

 <h2 align="center">XÂY DỰNG ỨNG DỤNG BÁN HÀNG CHO CỬA HÀNG THỜI TRANG GOLDIE</h2>

<p align="center">
    <img src="https://github.com/duogwas/goldie/blob/dev/goldie_github.jpg?raw=true" />
</p>

# **Mục lục**
- **Giới thiệu**
- **Tổng quan đề tài**
- **Một số giao diện ứng dụng**
  - [**1. Đăng nhập, đăng ký**](#1-đăng-nhập-đăng-ký)
  - [**2. Quên mật khẩu**](#2-quên-mật-khẩu)
  - [**3. Trang chủ**](#3-trang-chủ)
  - [**4. Danh sách sản phẩm**](#4-danh-sách-sản-phẩm)
  - [**5. Địa chỉ người dùng**](#5-địa-chỉ-người-dùng)
  - [**6. Thông tin đơn hàng**](#6-thông-tin-đơn-hàng)
  - [**7. Thanh toán**](#7-thanh-toán)
  - [**8. Thông báo**](#8-notification)

Chào các bạn, mình tên là Kiều Hoàng Dương. 

Lời đầu tiên mình xin chào các bạn và cảm ơn tất cả các bạn đang ở đây. Trong tài liệu này mình sẽ chia sẻ tất cả những gì các bạn cần biết khi làm đồ án 
tốt nghiệp và đề tài do mình thực hiện để các bạn có thể tham khảo. Mình hi vọng phần tài liệu mình viết tiếp theo đây 
sẽ hỗ trợ phần nào cho các bạn khi bước tới ngưỡng cửa quan trọng của cuộc đời mình - tốt nghiệp đại học.

Đồ án tốt nghiệp này có tất cả là 3 thành phần bao gồm:

* **API**
  
* **Website**
  
* **Ứng dụng Android** (Hiện tại)

Các bạn đang đọc phần mô tả chi tiết về `ứng dụng Android` trong đoán này trong tài liệu này mình sẽ mô tả chi tiết về các tính năng nổi bật nhất và một số những cái lưu ý khi các bạn tham khảo đồ án này.

# [**Tổng quan đề tài**](#tổng-quan-đề-tài)

Có thể giải thích yêu cầu đề tài ngắn gọn như sau:

**Website** - Đóng vai trò là ứng dụng quản trị viên. Hỗ trợ cửa hàng quản lý thông tin về các sản phẩm, đơn hàng, blog, voucher, tài khoản người dùng trên hệ thống.

**Android** - Ứng dụng để khách hàng có thể tiến hành mua sắm. 

# [**Một số giao diện ứng dụng**](#một-số-giao-diện-ứng-dụng)

Phần này mình sẽ giới thiệu về các giao diện và các chức năng chính trong ứng dụng

## [**1. Đăng nhập, đăng ký**](#1-đăng-nhập-đăng-ký)

<p align="center">
    <img src="https://github.com/duogwas/goldie/blob/main/Screenshots/gdchaomung.png?raw=true" height="600px"/>
</p>
<h3 align="center">

***Màn hình khởi chạy khi mở ứng dụng, màn hình đăng nhập, đăng ký và kích hoạt tài khoản***
</h3>

Ứng dụng cho phép người dùng đăng ký tài khoản qua email và xác thực người dùng qua mã OTP được gửi đến email:

1. Người dùng nhập các thông tin để đăng ký tài khoản

2. Người dùng sẽ nhận được email chứa mã OTP để kích hoạt tài khoản

## [**2. Quên mật khẩu**](#2-quên-mật-khẩu)

<p align="center">
    <img src="https://github.com/duogwas/goldie/blob/main/Screenshots/gdmatkhau.png?raw=true" height="600px"/>
</p>
<h3 align="center">

***Màn hình Quên mật khẩu***
</h3>

Ứng dụng hỗ trợ người dùng lấy lại mật khẩu qua email:

1. Người dùng nhập email đã được sử dụng để đăng ký tài khoản trên hệ thống

2. Người dùng sẽ nhận được email chứa mật khẩu mới, người dùng tiến hành đăng nhập cùng mật khẩu mới 


## [**3. Trang chủ**](#3-trang-chủ)

<p align="center">
    <img src="https://github.com/duogwas/goldie/assets/79986409/e3a3d90d-ad67-4cda-8423-9ac3c6c39845" height="600px"/>

</p>
<h3 align="center">

***Màn hình Trang chủ➡***
</h3>

Màn hình Home với hình ảnh minh họa bên từ trái qua là trình tự nội dung mà chúng ta sẽ nhìn thấy trên màn hình khi vuốt từ trái sang phải

Màn hình bao gồm các thành phần như sau:

1. Lời chào username
   
2. Logo
   
3. Banner 

4. Thanh tìm kiếm

5. Các danh mục sản phẩm

6. Danh sách các sản phẩm mới nhất

7. Thanh điều hướng với 4 menu chính: Trang chủ, Danh mục, Giỏ hàng và Cá nhân

## [**4. Danh sách sản phẩm**](#4-fullproduct)

<p align="center">
    <img src="https://github.com/duogwas/goldie/blob/main/Screenshots/gdfullsp.png?raw=true" height="600px"/>
</p>
<h3 align="center">

***Màn hình danh sách sản phẩm***
</h3>

Màn hình này sẽ hiển thị danh sách các sản phẩm của cửa hàng.

Ứng dụng hỗ trợ người dùng có thể tìm kiếm nhanh sản phẩm, lọc sản phẩm theo khoảng giá, danh mục, sắp xếp các sản phẩm theo điều kiện.

## [**5. Địa chỉ người dùng**](#5-địa-chỉ-người-dùng)

<p align="center">
    <img src="https://github.com/duogwas/goldie/blob/main/Screenshots/gddiachi.png?raw=true" height="600px"/>
</p>
<h3 align="center">

***Màn hình danh sách địa chỉ người dùng***
</h3>

Người dùng có thể xem danh sách các địa chỉ nhận hàng của mình tại đây, thêm mới một địa chỉ hoặc cập nhật các địa chỉ đã tạo trước đó

## [**6. Thông tin đơn hàng**](#6-thông-tin-đơn-hàng)

<p align="center">
    <img src="https://github.com/duogwas/goldie/blob/main/Screenshots/gddonhang.png?raw=true" height="600px"/>
</p>
<h3 align="center">

***Danh sách các đơn hàng và chi tiết đơn hàng 📅***
</h3>

Đơn hàng bao gồm các trạng thái: Chờ xác nhận, Chờ lấy hàng, Chờ giao hàng, Đã giao, Đã hủy. Mỗi trạng thái được chia làm một tab tương ứng

Khi ấn vào nút **Chi tiết**,  ứng dụng sẽ hiện thị thông tin chi tiết về đơn hàng  

## [**7. Thanh toán**](#7-thanh-toán)

Ứng dụng hỗ trợ người dùng 2 phương thức thanh toán:

1. Thanh toán khi nhận hàng
   
2. Thanh toán qua ví MoMo
   
Ở đây, do mình sử dụng máy ảo nên không mở được app MoMo dev để thanh toán, nên mình sử dụng WebView để thanh toán. 

Khi người dùng chọn Thanh toán bằng MoMo và nhấn nút Đặt hàng, ứng dụng sẽ chuyển tới WebView cổng thanh toán MoMo 

<p align="center">
    <img src="https://github.com/duogwas/goldie/blob/main/Screenshots/gdthanhtoanmomo.png?raw=true" height="600px"/>
</p>
<h3 align="center">

***Thanh toán đơn hàng bằng Ví MoMo***
</h3>

Ở phần thông tin thanh toán mình sẽ nhập bộ test của MoMo.

</h3>

## [**8. Notification**](#8-notification)

Ứng dụng có thể thông báo với người dùng qua  hình thức: Thông báo bằng thông báo hệ thống của Android System

Người dùng sẽ được gửi thông báo trong 2 trường hợp:

1. Đơn hàng được cập nhật trạng thái

2. Cửa hàng có Voucher mới

<p align="center">
    Phần này lười quá nên không chụp ảnh màn hình
</p>


# [**Tổng kết**](#post-script)

Phía trên là một sổ các chức năng và giao diện mà mình đã thực hiện trong trong đồ án tốt nghiệp này. 

Dương hi vọng phần chia sẻ ở phía trên sẽ ít nhiều đem lại hữu ích cho các bạn.

</h3>

<h1 align="center"> Made with 💘 from duogwas </h1>


