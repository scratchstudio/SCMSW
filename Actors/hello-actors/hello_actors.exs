defmodule Talker do
  def loop do # 자신을 재귀적으로 호출하는 방식으로 무한 루프
    receive do # 메시지가 전달되기를 기다림
      {:greet, name} -> IO.puts("Hello #{name}")
      {:praise, name} -> IO.puts("#{name}, you're amazing")
      {:celebrate, name, age} -> IO.puts("Here's to another #{age} yers, #{name}")
      {:shutdown} -> exit(:normal)
    end
    loop
  end
end

Process.flag(:trap_exit, true)
pid = spawn_link(&Talker.loop/0) # 액터의 인스턴스 생성하고 Talker 모듈 내부에 존재하는 아무 인수도 받지 않는 loop 실행

send(pid, {:greet, "Huey"})
send(pid, {:praise, "Dewey"})
send(pid, {:celebrate, "Louie", 16})
send(pid, {:shutdown})

receive do
  {:EXIT, ^pid, reason} -> IO.puts("Talker has exited (#{reason})")
end
