# 스레드와 잠금장치

## 멀티스레드 코드가 잘못될 수 있는 경우

1. 경쟁조건<sup>race condition</sup>
2. 메모리 가시성
3. 교착상태<sup>dead lock</sup>



### 위험 요소를 피하는데 도움이 되는 규칙들

- 공유되는 변수에 대한 접근을 반드시 동기화
- 쓰는 스레드와 읽는 스레드 모두 동기화
- 여러개 잠금장치를 미맂 어해진 공통의 순서에 따라 요청
- 잠금장치를 가진 상태에서 외부 메서드를 호출하지 않음
- 잠금장치는 최대한 짧게 보유



## 참고자료

- [윌리엄푸의 자바 메모리 모델](http://www.cs.umd.edu/~pugh/java/memoryModel/index.html#reference)
- [JSR 133 (Java Memory Model) FAQ 번역](http://qwefgh90.github.io/java/JSR-133-(Java-Memory-Model)-FAQ(%EB%B2%88%EC%97%AD)/)
- [Memory Visibility(메모리 가시성) 와 Memory Barrier(메모리 장벽)](http://blog.naver.com/jjoommnn/130037479493)
- [디자인 패턴으로 알아본 Double Checked Lock(DCL)](http://www.hanbit.co.kr/media/channel/view.html?cms_code=CMS6818849791)