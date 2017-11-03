defmodule Counter do
  def start(count) do
    pid = spawn(__MODULE__, :loop, [count]) # 모듈이름, 함수이름, 함수에 전달될 인수
    Process.register(pid, :counter) # 프로세스 식별자 대신 이름을 사용
    pid
  end
  def next do
    ref = make_ref() # 고유의 참조 주소 생성
    send(:counter, {:next, self(), ref})
    receive do
      {:ok, ^ref, count} -> count
    end
  end
  def loop(count) do
    receive do
      {:next, sender, ref} ->
        send(sender, {:ok, ref, count})
        #IO.puts("Current count: #{count}")
        loop(count + 1)
    end
  end
end

# iex counter.ex
# Counter.start(42)
# Counter.next(counter)
# Counter.next(counter)
