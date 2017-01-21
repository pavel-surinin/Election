var DistrictListViewComponent = React.createClass({

  render: function() {
    var array = [];
    this.props.districtList.map(function(dist,index) {
      array.push(
				<DistrictListViewRowComponent
				id={dist.id}
				key={index}
				name={dist.name}
				adress={dist.adress}
				countyId={dist.countyId}
				countyName={dist.countyName}
				representativeId={dist.representativeId}
				representativeName={dist.representativeName}
				/>
			);
    });

    return (
      <div className="panel panel-default">
        <div className="panel-heading"><h2>Apylinkių sąrašas</h2></div>
          <div className="panel-body">
            <span>
              <h5>Registruoti naują apylinkę </h5>
            </span>
            <a href="#/admin/district/create"><button type="button" className="btn btn-success btn-sm">Registruoti</button></a>
          </div>
            <table className="table table-striped">
            <thead>
            <tr>
              <th>
                Eil. Nr.
              </th>
              <th>
                Pavadinimas
              </th>
              <th>
                Adresas
              </th>
              <th>
                Apygarda
              </th>
              <th>
                Atstovas
              </th>
              <th>
                Veiksmai
              </th>
            </tr>
            </thead>
            <tbody>
							{array}
            </tbody>
          </table>
        </div>
    );
  }

});

window.DistrictListViewComponent = DistrictListViewComponent;
