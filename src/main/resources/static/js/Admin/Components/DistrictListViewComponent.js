var DistrictListViewComponent = React.createClass({
  render: function() {
    var succesCreateMessage = alerts.showSucces(this.props.succesCreateText);
    var self = this.props;
    var array = [];
    var self= this.props;
    this.props.districtList.map(function(dist,index) {
      array.push(
				<DistrictListViewRowComponent
				id={dist.id}
				key={index}
        index={index}
				name={dist.name}
				adress={dist.adress}
				countyId={dist.countyId}
				countyName={dist.countyName}
				representativeId={dist.representativeId}
				representativeName={dist.representativeName}
        onHandleDelete={self.onHandleDelete}
				/>
			);
    });

    return (
		      <div className="panel panel-default">
		        <div className="panel-heading"><h2>Apylinkių sąrašas</h2></div>
		          <div className="panel-body">
                {succesCreateMessage}
		            <span>
		              <h5>Registruoti naują apylinkę </h5>
		            </span>
		            <a href="#/admin/district/create"><button type="button" id="register-button" className="btn btn-success btn-sm">Registruoti</button></a>
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
