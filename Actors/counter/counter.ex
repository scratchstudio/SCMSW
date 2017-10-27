defmodule Counter do
  def loop(count) do
    receive do
      {:next} ->
        IO.puts("Current count: #{count}")
        loop(count + 1)
    end
  end
end

counter = spawn(Counter, :loop, [1]) # 모듈이름, 함수이름, 함수에 전달될 인수

# 가변 변수를 전혀 사용하지 않고 상태를 보관하는 것처럼 보이는 액터
send(counter, {:next})
send(counter, {:next})
send(counter, {:next})
