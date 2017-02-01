var CandidateEditFormComponent = React.createClass({
	  render: function() {
				var nameErr = validation.showMsg(this.props.nameErrorMesages);
				var surnameErr = validation.showMsg(this.props.surnameErrorMesages);
		  return (
					        <div className="row">
					          <div className="col-md-6 col-md-offset-3 col-xs-12">
					            <div className="panel panel-primary">
											<div className="panel-heading text-center">
											<h4><b>Atnaujinti kandidato informaciją</b></h4>
											</div>
					              <div className="panel-body">
					              	<div className="form-heading">
					              	</div>
					              	<form onSubmit={this.props.onHandleSubmit} role="form">
					                <div className="input-group col-xs-12 text-primary">
					                <label>Kandidato vardas</label>
					                  <input
					                    type="text"
					                    onChange={this.props.onHandleNameChange}
					                    value={this.props.name}
					                    className="form-control"
					                    placeholder="Kandidato vardas"
					                    required
					                  />
					                </div>
													<br/>
													{nameErr}
					                <div className="input-group col-xs-12 text-primary">
					                <label>Kandidato pavardė</label>
					                  <input
					                    type="text"
					                    onChange={this.props.onHandleSurnameChange}
					                    value={this.props.surname}
					                    className="form-control"
					                    placeholder="Kandidato pavardė"
					                    required
					                  />
					                </div><br/>
													{surnameErr}
													<div className="input-group col-xs-12 text-primary">
													<label>Kandidato gimimo data</label>
														<input
															type="text"
															onChange={this.props.onHandleBirthDateChange}
															value={this.props.birthDate}
															className="form-control"
															placeholder="Gimimo data"
															required
														/>
													</div><br/>
													<div className="input-group col-xs-12 text-primary">
													<label>Kandidato aprašymas</label>
														<input
															type="text"
															onChange={this.props.onHandleDescriptionChange}
															value={this.props.description}
															className="form-control"
															placeholder="Aprašymas"
														/>
													</div><br/>
													<div className="input-group col-xs-12 text-primary">
													<label>Kandidato numeris partijoje</label>
														<input
															type="text"
															onChange={this.props.onHandleNumberInPartyChange}
															value={this.props.numberInParty}
															className="form-control"
															placeholder="Numeris partijoje"
														/>
													</div><br/>
													<div className='text-center'>
					                <button className='btn btn-success btn-outline col-xs-5'>
					                  {this.props.action}
					                </button>
													<a className="btn btn-danger btn-outline col-xs-5 col-xs-offset-2" href="#/admin/candidate" role="button">Atšaukti</a>
													</div>
					                </form>
					              </div>
					            </div>
					          </div>
					        </div>
		    );
		  }
		});

window.CandidateEditFormComponent = CandidateEditFormComponent;
