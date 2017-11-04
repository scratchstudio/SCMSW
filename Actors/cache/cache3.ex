defmodule Cache do
  use GenServer
  def handle_cast({:put, url, page}, {pages, size}) do
    new_pages = Dict.put(pages, url, page)
    new_size = size + byte_size(page)
    {:noreply, {new_pages, new_size}}
  end

  def handle_call({:get, url}, _from, {pages, size}) do
    {:reply, pages[url], {pages, size}}
  end

  def handle_call({:size}, _from, {pages, size}) do
    {:reply, size, {pages, size}}
  end

  #####
  # External API
  def start_link do
    :gen_server.start_link({:local, :cache}, __MODULE__, {HashDict.new, 0}, [])
  end

  def put(url, page) do
    :gen_server.cast(:cache, {:put, url, page})
  end

  def get(url) do
    :gen_server.call(:cache, {:get, url})
  end

  def size do
    :gen_server.call(:cache, {:size})
  end
end

defmodule CacheSupervisor do
  use Supervisor

  def init(_args) do
    workers = [worker(Cache, [])]
    supervise(workers, strategy: :one_for_one)
    # 재시작 전략은 일대일, 혹은 일대다가 가장 많이 쓰임.
    # 일대다 전략을 사용하는 감시자는 모든 작업자를 재시작.
    # 반면 일대일 전략은 망가진 작업자만 특정해서 재시작.
  end

  def start_link do
    :supervisor.start_link(__MODULE__, [])
  end
end
