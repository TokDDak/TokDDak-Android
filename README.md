# TokDDak-Android

## 프로젝트 사용 라이브러리
* androidx: recyclerview, viewpager 등 이용
* glide: 이미지 로딩
* CircleImageView: 동그란 이미지 커스텀 뷰
* retrofit : 통신
* gson : 객체 시리얼 라이즈
* material : modal bottom sheet

## 프로그램 구조(패키지 구조)
* api: 통신 간 사용되는 interface, Object 
* data: ui 출력 및 통신 시 사용할 데이터 클래스 코드
* feature: 프로그램 기능별 코드 정리
  * 각 기능에서 사용한 activity, fragment, adapter 코드 등
* (JM) 패키지
  * bottom : Modal Bottom Sheet 프래그먼트(하단 뷰)
  * day : 일정 뷰(데이터, feature)
  * inner : 일정 뷰 내에 있는 RecyclerView
  * expense : 지출 입력(")
  * history : 지출 입력 내용을 보여줌
  
## 핵심 기능 구현 방법
### 경비-일정
* 경비 일정 페이지에 Modla Bottom Sheet(Fragment), Day Activity 1(RecyclerView 2(중첩))
* 리사이클러뷰(Fragment 내부)에 있는 버튼 아이템을 클릭하여 contents들을 Day Activity에 있는 RecyclerView 내 RecyclerView item에 추가
* 아이템들의 매핑된 가격을 Total로 계산 (매핑 가격은 서버에서 가져온다.)
### 지출-입력
* category, cost, content를 서버로 보낸다. (category는 6개 중 택 1만 가능)
* 서버로 보낸 데이터들을 지출-입력 History에서 서버를 통해 받는다.


(람다와 extension function
Constraint layout
라이버러리 및 용례
프로그램 구조
핵심 기능 구현 방법 정리)
