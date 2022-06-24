package br.com.zenix.lobby.hologram;

public class PlayerTop {

	private String name;
	private Integer id;
	private int top;

	public PlayerTop(Integer id, String name, int top) {
		this.id = id;
		this.name = name;
		this.top = top;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getTop() {
		return top;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public static class PlayerTopHGWins {

		private String name;
		private Integer id;
		private int top;

		public PlayerTopHGWins(Integer id, String name, int top) {
			this.id = id;
			this.name = name;
			this.top = top;
		}

		public Integer getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public int getTop() {
			return top;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setTop(int top) {
			this.top = top;
		}
	}

	public static class PlayerTopSWDeaths {

		private String name;
		private Integer id;
		private int top;

		public PlayerTopSWDeaths(Integer id, String name, int top) {
			this.id = id;
			this.name = name;
			this.top = top;
		}

		public Integer getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public int getTop() {
			return top;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setTop(int top) {
			this.top = top;
		}
	}

	public static class PlayerTopSWElo {

		private String name;
		private Integer id;
		private int top;

		public PlayerTopSWElo(Integer id, String name, int top) {
			this.id = id;
			this.name = name;
			this.top = top;
		}

		public Integer getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public int getTop() {
			return top;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setTop(int top) {
			this.top = top;
		}
	}

	public static class PlayerTopSWWins {

		private String name;
		private Integer id;
		private int top;

		public PlayerTopSWWins(Integer id, String name, int top) {
			this.id = id;
			this.name = name;
			this.top = top;
		}

		public Integer getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public int getTop() {
			return top;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setTop(int top) {
			this.top = top;
		}
	}

	public static class PlayerTopPvP {

		private String name;
		private Integer id;
		private int top;

		public PlayerTopPvP(Integer id, String name, int top) {
			this.id = id;
			this.name = name;
			this.top = top;
		}

		public Integer getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public int getTop() {
			return top;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setTop(int top) {
			this.top = top;
		}
	}

	public static class PlayerTopClans {

		private String name;
		private Integer id;
		private int top, wins;

		public PlayerTopClans(Integer id, String name, int top, int wins) {
			this.id = id;
			this.name = name;
			this.top = top;
			this.wins = wins;
		}

		public Integer getId() {
			return id;
		}

		public String getNameClan() {
			return name;
		}

		public int getTop() {
			return top;
		}
		
		public int getWins() {
			return wins;
		}

		public void setWins(int wins) {
			this.wins = wins;
		}
		
		public void setId(Integer id) {
			this.id = id;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setTop(int top) {
			this.top = top;
		}
	}
}
