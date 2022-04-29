package com.cloudmore.kafkatask.consumer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity(name = "client")
public class ClientEntity {

	@Id
	// TODO: Migrate to better DB or fix MySQL identity shenanigans
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	@NotBlank
	@Size(max = 250)
	@Column
	private String name;

	@NotBlank
	@Size(max = 250)
	@Column
	private String surname;

	@PositiveOrZero
	@Column
	private Double wage;

	@NotNull
	@Column(name = "event_time")
	private Instant eventTime;

	@Override
	public boolean equals(final Object obj) {
		if(this == obj) {
			return true;
		}
		if(!(obj instanceof ClientEntity)) {
			return false;
		}
		final ClientEntity that = (ClientEntity)obj;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
