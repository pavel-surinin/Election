var DistrictRepresentativeCreateFormComponent = React.createClass({
	  render: function() {

				//this.props.nameErrorMesages

				//this.props.surnameErrorMesages
		    var districts = [];
		    this.props.districtList.map(function(district,index) {
		    	districts.push(
		        <option key={index} value={district.id}>{district.name}</option>
		      );
		    });
				var nameErr = validation.showMsg(this.props.nameErrorMesages);
				var surnameErr = validation.showMsg(this.props.surnameErrorMesages);
		    return (
					        <div className="row">
					          <div className="col-md-6 col-md-offset-3 col-xs-12">
					            <div className="panel panel-primary">
											<div className="panel-heading text-center">
											<h4><b>Registruoti apylinkės atstovą</b></h4>
											</div>
					              <div className="panel-body">
					              	<div className="form-heading">
					              	</div>
					              	<form onSubmit={this.props.onHandleSubmit} role="form">
					                <div className="input-group col-xs-12 text-primary">
					                <label>Atstovo vardas</label>
					                  <input
					                    type="text"
					                    onChange={this.props.onHandleNameChange}
					                    value={this.props.name}
					                    className="form-control"
					                    placeholder="Vardas"
					                    required
					                  />
					                </div>
													<br/>
													{nameErr}
					                <div className="input-group col-xs-12 text-primary">
					                <label>Atstovo pavardė</label>
					                  <input
					                    type="text"
					                    onChange={this.props.onHandleSurnameChange}
					                    value={this.props.surname}
					                    className="form-control"
					                    placeholder="Pavardė"
					                    required
					                  />
					                </div><br/>
													{surnameErr}
					                <div className="form-group text-primary">
					                  <label>Atstovaujama apylinkė:</label>
					                  <select value={this.props.district} onChange={this.props.onHandleDistrictChange} className="form-control" required>
					                    {districts}
					                  </select>
					                </div>
													<br/>
													<div className='text-center'>
					                <button className='btn btn-success btn-outline col-xs-5'>
					                  {this.props.action}
					                </button>
													<a className="btn btn-danger btn-outline col-xs-5 col-xs-offset-2" href="#/admin/representative" role="button">Atšaukti</a>
													</div>
					                </form>

					              </div>
					            </div>
					          </div>
					        </div>
		    );
		  }
		});

window.DistrictRepresentativeCreateFormComponent = DistrictRepresentativeCreateFormComponent;
