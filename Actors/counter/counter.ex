defmodule Counter do
  def start(count) do
    spawn(__MODULE__, :loop, [count]) # 모듈이름, 함수이름, 함수에 전달될 인수
  end
  def next(counter) do
    send(counter, {:next})
  end
  def loop(count) do
    receive do
      {:next} ->
        IO.puts("Current count: #{count}")
        loop(count + 1)
    end
  end
end

counter = Counter.start(42)
Counter.next(counter)
Counter.next(counter)
